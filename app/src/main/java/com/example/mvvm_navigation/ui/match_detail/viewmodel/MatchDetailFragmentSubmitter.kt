package com.example.mvvm_navigation.ui.match_detail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.base.extension.default
import com.example.mvvm_navigation.datacenter.network.response.MatchDetail

class MatchDetailFragmentSubmitter {

    val matchDetail = MutableLiveData<MatchDetail.Data>()
    val matchDetailString = MutableLiveData<String>().default("")

}