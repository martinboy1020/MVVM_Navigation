package com.example.mvvm_navigation.ui.main.home.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.TgMatchRecent
import com.example.mvvm_navigation.ui.main.home.viewmodel.recent_match.RecentMatchContract

class RecentMatchModel constructor(val repository: Repository): BaseModel(), RecentMatchContract.ModelImpl{

    companion object{
        fun getInstance(repository: Repository) =
            RecentMatchModel(repository)
    }

    override suspend fun getTgMatchesRecent(timeKey: String): HttpResult<HttpStatus<MutableList<TgMatchRecent.Recent>>> {
        return when(val response = this@RecentMatchModel.repository.getTgMatchesRecent(timeKey)){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

}