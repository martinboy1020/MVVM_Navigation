package com.example.mvvm_navigation.ui.main.home.model

import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.Login
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeContract

class HomeModel constructor(val repository: Repository): BaseModel(), HomeContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) =
            HomeModel(repository)
    }

    override suspend fun getUserData(): HttpResult<List<UserData.User>> {
        return when(val response = this@HomeModel.repository.getUser()){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

    override fun getMatchList(): MutableList<MatchListItem> {
        return this@HomeModel.repository.getMatchList()
    }

    override suspend fun getBannerData(): MutableList<BannerItem> {
        return this@HomeModel.repository.getBannerList()
    }

    override suspend fun userLogin(username: String, password: String, type: Int): HttpResult<Login.UserLogin> {
        return when(val response = this@HomeModel.repository.userLogin(username, password, type)){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }
}