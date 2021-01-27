package com.example.mvvm_navigation.ui.match_detail.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchDetail
import com.example.mvvm_navigation.ui.match_detail.viewmodel.MatchDetailContract

class MatchDetailModel constructor(val repository: Repository) : BaseModel(), MatchDetailContract.ModelImpl {
    companion object {
        fun getInstance(repository: Repository) =
            MatchDetailModel(
                repository
            )
    }

    override suspend fun getMatchDetail(matchId: Int): HttpResult<HttpStatus<MatchDetail.Data>> {
        return repository.getMatchDetail(matchId)
    }
}