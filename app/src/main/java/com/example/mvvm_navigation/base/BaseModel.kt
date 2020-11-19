package com.example.mvvm_navigation.base

import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData

open class BaseModel {

    open suspend fun getUserData(): HttpResult<List<UserData.User>> {
        TODO("Not yet implemented")
    }
}