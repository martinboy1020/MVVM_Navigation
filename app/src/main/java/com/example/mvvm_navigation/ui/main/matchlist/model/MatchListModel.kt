package com.example.mvvm_navigation.ui.main.matchlist.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.ui.main.matchlist.viewmodel.MatchListContract

class MatchListModel constructor(val repository: Repository) : BaseModel(), MatchListContract.ModelImpl {
    companion object {
        fun getInstance(repository: Repository) =
            MatchListModel(
                repository
            )
    }

//    override suspend fun getUser(): HttpResult<List<UserData.User>> {
//        return when(val response = this@MatchListModel.repository.getUser()){
//            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
//            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
//        }
//    }

    override fun setMatchItemToTopList(data: MatchList.Match): MutableList<MatchList.Match> {
        return this@MatchListModel.repository.refreshTopListMatch(data)
    }

    override suspend fun getMatchesList(date: Long): HttpResult<HttpStatus<MatchList.Data>> {
        return this@MatchListModel.repository.getWebMatchList(date)
    }

    override fun getTestMatchesList(): HttpStatus<MatchList.Data>? {
        return this@MatchListModel.repository.getTestMatchListData()
    }

}