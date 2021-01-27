package com.example.mvvm_navigation.ui.match.matchlist.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.match.matchlist.MatchListAdapter
import com.example.mvvm_navigation.ui.match.matchlist.MatchListFragment
import com.example.mvvm_navigation.utils.GameStatusUtils

class MatchListFragmentSubmitter {
    val pageType = MutableLiveData<Int>().default(MatchListFragment.MATCH_ING)
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val matchList = MutableLiveData<MutableList<MatchList.Match>>().default(mutableListOf())
    val areaList = MutableLiveData<MutableList<MatchList.Area>>().default(mutableListOf())
    val matchListAdapterListener = MutableLiveData<MatchListAdapter.MatchListAdapterItemClickListener>()
    val viewModelToFragmentListener = MutableLiveData<MatchListViewModel.ViewModelToFragmentListener>()
}