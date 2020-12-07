@file:Suppress("UNCHECKED_CAST")

package com.example.mvvm_navigation.utils

import android.graphics.Color
import android.util.Log
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.SecondAdapter
import com.example.mvvm_navigation.ui.main.vm.BannerAdapter
import com.example.mvvm_navigation.widget.BuildRecyclerView
import com.to.aboomy.pager2banner.Banner
import com.to.aboomy.pager2banner.IndicatorView

object AdapterUtils {

    object Adapters {
        const val SECOND = "second"
    }

    @JvmStatic
    @BindingAdapter("adapter", "data", "listener", requireAll = false)
    fun BuildRecyclerView.Adapters(adapter: String, data: Any?, listener: Any?) {
        this.adapter = when (adapter) {
            Adapters.SECOND -> SecondAdapter(
                this.context,
                data as MutableList<UserData.User>,
                listener as SecondAdapter.SecondAdapterItemClickListener
            )
            else -> null
        }
    }

    @JvmStatic
    @BindingAdapter("data", "listener", requireAll = false)
    fun setBannerData(banner: Banner, data: List<BannerItem>, listener: Any?) {
        if(!data.isNullOrEmpty()) {
            banner.isAutoPlay = true
            banner.setOuterPageChangeListener(object : ViewPager2.OnPageChangeCallback() {})
            val bannerAdapter = BannerAdapter()
            bannerAdapter.setData(data)
            banner.setOffscreenPageLimit(data.size)
            banner.setAutoTurningTime(3000)
            banner.adapter = bannerAdapter
        }
    }

}