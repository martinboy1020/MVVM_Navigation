package com.example.mvvm_navigation.ui.main.home.viewmodel.home

import com.example.mvvm_navigation.base.BaseViewModelImpl
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.Login
import com.example.mvvm_navigation.datacenter.network.response.UserData

class HomeContract {
    interface ModelImpl {
        suspend fun getUserData(): HttpResult<List<UserData.User>>
        suspend fun getBannerData(): MutableList<BannerItem>
        suspend fun userLogin(username: String, password: String, type: Int): HttpResult<HttpStatus<Login.UserLogin>>
        suspend fun userTokenRefresh(token: String): HttpResult<HttpStatus<Login.TokenRefresh>>
        fun getMatchList(): MutableList<MatchListItem>
    }

    interface ViewModelImpl : BaseViewModelImpl<HomeFragmentSubmitter> {
        fun drawerNavigationClick(itemId: Int)
    }
}