package com.example.mvvm_navigation.ui.filter.viewmodel.match_filter

import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.network.response.MatchList

class MatchFilterFragmentSubmitter {

    val areaList = MutableLiveData<MutableList<MatchList.Area>>().default(mutableListOf())
    val countryList = MutableLiveData<MutableList<MatchList.Country>>().default(mutableListOf())
    val leagueList = MutableLiveData<MutableList<MatchList.Leagues>>().default(mutableListOf())

}