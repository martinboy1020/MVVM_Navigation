package com.example.mvvm_navigation.utils

import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.network.response.MatchesStatistics
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.ui.main.home.BetListAdapter
import com.example.mvvm_navigation.ui.main.home.MatchesRecentAdapter
import com.example.mvvm_navigation.ui.main.home.RecentConditionAdapter
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter
import com.example.mvvm_navigation.widget.BuildRecyclerView


object AdapterUtils {

    @JvmStatic
    @BindingAdapter("matchesRecentList", "listener", requireAll = false)
    fun BuildRecyclerView.matchesRecentListAdapter(matchesRecentList: MutableList<TgMatchRecent.Recent>?, listener: Any?) {
        this.adapter =
            MatchesRecentAdapter(
                this.context,
                matchesRecentList as MutableList<TgMatchRecent.Recent>,
                listener as MatchesRecentAdapter.MatchListAdapterItemClickListener
            )
    }

    @JvmStatic
    @BindingAdapter("matchList", "listener", requireAll = false)
    fun BuildRecyclerView.matchListAdapter(
        matchList: MutableList<MatchList.Match>?,
        listener: Any?
    ) {
        if (matchList != null) {
            if (this.adapter == null) {
                this.adapter =
                    MatchListAdapter(
                        this.context,
                        matchList,
                        if (listener != null) listener as MatchListAdapter.MatchListAdapterItemClickListener else null
                    )
            } else {
                (this.adapter as MatchListAdapter).data = matchList
                (this.adapter as MatchListAdapter).notifyDataSetChanged()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("recentMatchConditionList", "listener", requireAll = false)
    fun Spinner.setEntries(
        entries: MutableList<MatchesStatistics.Season>,
        listener: AdapterView.OnItemSelectedListener
    ) {
        this.adapter =
            RecentConditionAdapter(
                this.context,
                R.layout.support_simple_spinner_dropdown_item,
                entries
            )
        this.onItemSelectedListener = listener
    }

    @JvmStatic
    @BindingAdapter("betList", requireAll = false)
    fun BuildRecyclerView.betInfoListAdapter(matchList: MutableList<BetData>?) {
        this.adapter = BetListAdapter(
            this.context,
            matchList as MutableList<BetData>
        )
    }

}