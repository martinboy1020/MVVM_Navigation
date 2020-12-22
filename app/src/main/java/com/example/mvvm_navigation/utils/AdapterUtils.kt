@file:Suppress("UNCHECKED_CAST")

package com.example.mvvm_navigation.utils

import android.R
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.ui.main.home.BetListAdapter
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.ui.main.home.viewmodel.RecentConditionAdapter
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter
import com.example.mvvm_navigation.widget.BuildRecyclerView


object AdapterUtils {

    object Adapters {
        const val SECOND = "second"
    }

    @JvmStatic
    @BindingAdapter("matchesRecentList", "listener", requireAll = false)
    fun BuildRecyclerView.MatchesRecentListAdapter(matchesRecentList: Any?, listener: Any?) {
        this.adapter =
            MatchesRecentAdapter(
                this.context,
                matchesRecentList as MutableList<TgMatchRecent.Recent>,
                listener as MatchesRecentAdapter.MatchListAdapterItemClickListener
            )
    }

    @JvmStatic
    @BindingAdapter("matchList", "listener", requireAll = false)
    fun BuildRecyclerView.MatchListAdapter(matchList: Any?, listener: Any?) {
        this.adapter =
            MatchListAdapter(
                this.context,
                matchList as MutableList<MatchListItem>,
                if(listener != null) listener as MatchListAdapter.MatchListAdapterItemClickListener else null
            )
    }

    @JvmStatic
    @BindingAdapter("recentMatchConditionList")
    fun Spinner.setEntries(entries: List<RecentMatchCondition>) {
        this.adapter = RecentConditionAdapter(this.context, R.layout.simple_dropdown_item_1line, entries)
    }

    @JvmStatic
    @BindingAdapter("betList", requireAll = false)
    fun BuildRecyclerView.BetInfoListAdapter(matchList: Any?) {
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