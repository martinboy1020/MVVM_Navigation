package com.example.mvvm_navigation.datacenter.network

import com.example.mvvm_navigation.datacenter.network.response.UserData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiMethod {
    @GET(ApiConstants.HttpPath.Users)
    fun getUsers(): Deferred<List<UserData.User>>

    @GET(ApiConstants.HttpPath.Users)
    fun getUserFindIndex(@Path("index") index: Int
    ): Deferred<List<UserData.User>>
}