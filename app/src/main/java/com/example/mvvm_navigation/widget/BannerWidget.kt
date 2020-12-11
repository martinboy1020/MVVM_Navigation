package com.example.mvvm_navigation.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.ui.main.vm.BannerAdapter
import com.to.aboomy.pager2banner.Banner
import com.to.aboomy.pager2banner.IndicatorView

@BindingAdapter("bannerData", "showDots", "listener", requireAll = false)
fun setBanner(
    view: BannerWidget,
    data: MutableList<BannerItem>? = null,
    showDots: Boolean? = true,
    listener: BannerWidget.BannerClickListener? = null
) {
    if (!data.isNullOrEmpty()) {
        view.setBannerData(data, showDots, listener)
    }
}

class BannerWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface BannerClickListener {
        fun click(position: Int)
    }

    private var view: View? = null

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_banner, this)
    }

    fun setBannerData(data: MutableList<BannerItem>, showDots: Boolean? = true, listener: BannerClickListener? = null) {
        val banner = findViewById<Banner>(R.id.banner)
        if (!data.isNullOrEmpty()) {
            banner.isAutoPlay = true
            banner.setOuterPageChangeListener(object : ViewPager2.OnPageChangeCallback() {})
           setDots(banner, showDots)
            val bannerAdapter = BannerAdapter(listener)
            bannerAdapter.setData(data)
            banner.setOffscreenPageLimit(data.size)
            banner.setAutoTurningTime(3000)
            banner.adapter = bannerAdapter
        }
    }

    private fun setDots(banner: Banner, showDots: Boolean? = true) {
        val indicatorView = findViewById<IndicatorView>(R.id.indicatorView)
        banner.setIndicator(indicatorView.setIndicatorColor(Color.WHITE)
            .setIndicatorRadius(8f)
            .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_CIRCLE)
            .setIndicatorSelectorColor(Color.RED), false)
        if(showDots == true) {
            indicatorView.visibility = View.VISIBLE
        } else {
            indicatorView.visibility = View.GONE
        }
    }

}