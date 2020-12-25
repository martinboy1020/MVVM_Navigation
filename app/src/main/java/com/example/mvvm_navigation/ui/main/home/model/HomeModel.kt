package com.example.mvvm_navigation.ui.main.home.model

import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.*
import com.example.mvvm_navigation.ui.main.home.viewmodel.home.HomeContract

class HomeModel constructor(val repository: Repository): BaseModel(), HomeContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) =
            HomeModel(repository)
    }

//    override suspend fun getUserData(): HttpResult<List<UserData.User>> {
//        return when(val response = this@HomeModel.repository.getUser()){
//            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
//            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
//        }
//    }

    override suspend fun getBannerData(banners: Home.WebHomeInfo): MutableList<BannerItem> {
        return this@HomeModel.repository.getBannerList(banners)
    }

    override suspend fun userLogin(username: String, password: String, type: Int): HttpResult<HttpStatus<Login.UserLogin>> {
        return when(val response = this@HomeModel.repository.userLogin(username, password, type)){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

    override suspend fun userTokenRefresh(token: String): HttpResult<HttpStatus<Login.TokenRefresh>> {
        return when(val response = this@HomeModel.repository.refreshUserToken(token)){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

    override suspend fun getHomeInfo(): HttpResult<HttpStatus<Home.WebHomeInfo>> {
        return when(val response = this@HomeModel.repository.getHomeInfo()){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

    override suspend fun getTgMatchesRecent(timeKey: String): HttpResult<HttpStatus<MutableList<TgMatchRecent.Recent>>> {
        return when(val response = this@HomeModel.repository.getTgMatchesRecent(timeKey)){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

}