package com.example.mvvm_navigation.ui.match_detail.viewmodel

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchDetail
import com.example.mvvm_navigation.datacenter.network.response.MatchList

class MatchDetailContract {
    interface ModelImpl {
       suspend fun getMatchDetail(matchId: Int): HttpResult<HttpStatus<MatchDetail.Data>>
    }
    interface ViewModelImpl : BaseViewModelImpl<MatchDetailFragmentSubmitter> {
        fun getMatchDetail(matchId: Int)
    }
}