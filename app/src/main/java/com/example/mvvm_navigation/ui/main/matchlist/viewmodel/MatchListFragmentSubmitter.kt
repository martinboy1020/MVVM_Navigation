package com.example.mvvm_navigation.ui.main.matchlist.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.main.matchlist.MatchListAdapter

class MatchListFragmentSubmitter {
    val onClickListener = MutableLiveData<View.OnClickListener>()
    val matchList = MutableLiveData<MutableList<MatchList.Match>>().default(mutableListOf())
    val matchListAdapterListener = MutableLiveData<MatchListAdapter.MatchListAdapterItemClickListener>()
}