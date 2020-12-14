@file:Suppress("UNCHECKED_CAST")

package com.example.mvvm_navigation.utils

import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.BetListAdapter
import com.example.mvvm_navigation.ui.main.MatchListAdapter
import com.example.mvvm_navigation.ui.main.SecondAdapter
import com.example.mvvm_navigation.widget.BuildRecyclerView


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
    @BindingAdapter("matchList", "listener", requireAll = false)
    fun BuildRecyclerView.Adapters(matchList: Any?, listener: Any?) {
        this.adapter = MatchListAdapter(
            this.context,
            matchList as MutableList<MatchListItem>,
            listener as MatchListAdapter.MatchListAdapterItemClickListener
        )
    }

    @JvmStatic
    @BindingAdapter("betList", requireAll = false)
    fun BuildRecyclerView.Adapters(matchList: Any?) {
        this.adapter = BetListAdapter(
            this.context,
            matchList as MutableList<BetData>
        )
    }

//    @JvmStatic
//    @BindingAdapter("data", "listener", requireAll = false)
//    fun setBannerData(banner: Banner, data: List<BannerItem>, listener: Any?) {
//        if(!data.isNullOrEmpty()) {
//            banner.isAutoPlay = true
//            banner.setOuterPageChangeListener(object : ViewPager2.OnPageChangeCallback() {})
//            val bannerAdapter = BannerAdapter()
//            bannerAdapter.setData(data)
//            banner.setOffscreenPageLimit(data.size)
//            banner.setAutoTurningTime(3000)
//            banner.adapter = bannerAdapter
//        }
//    }

}