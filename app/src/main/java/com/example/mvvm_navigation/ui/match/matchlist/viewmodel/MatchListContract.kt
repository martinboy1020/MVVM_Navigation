package com.example.mvvm_navigation.ui.match.matchlist.viewmodel

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import java.sql.Timestamp

class MatchListContract {
    interface ModelImpl {
        //        suspend fun getUser(): HttpResult<List<UserData.User>>
        fun setMatchItemToTopList(data: MatchList.Match): MutableList<MatchList.Match>
        suspend fun getMatchesList(date: Long): HttpResult<HttpStatus<MatchList.Data>>
        fun getTestMatchesList(): HttpStatus<MatchList.Data>?
    }

    interface ViewModelImpl : BaseViewModelImpl<MatchListFragmentSubmitter> {
        fun changeDate(timestamp: Long)
    }
}