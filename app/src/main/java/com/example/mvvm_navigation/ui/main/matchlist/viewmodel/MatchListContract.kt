package com.example.mvvm_navigation.ui.main.matchlist.viewmodel

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData

class MatchListContract {
    interface ModelImpl{
        suspend fun getUser(): HttpResult<List<UserData.User>>
        fun getMatchList(): MutableList<MatchListItem>
        fun setMatchItemToTopList(data: MatchListItem): MutableList<MatchListItem>
    }

    interface ViewModelImpl: BaseViewModelImpl<MatchListFragmentSubmitter> {

    }
}