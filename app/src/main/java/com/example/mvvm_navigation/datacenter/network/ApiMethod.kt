package com.example.mvvm_navigation.datacenter.network

import com.example.mvvm_navigation.datacenter.network.response.HttpStatus
import com.example.mvvm_navigation.datacenter.network.response.Login
import com.example.mvvm_navigation.datacenter.network.response.UserData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiMethod {
    @GET(ApiConstants.HttpPath.Users)
    fun getUsers(): Deferred<List<UserData.User>>

    @GET(ApiConstants.HttpPath.Users)
    fun getUserFindIndex(
        @Path("index") index: Int
    ): Deferred<List<UserData.User>>

    @POST(ApiConstants.HttpPath.LOGIN)
    @FormUrlEncoded
    fun userLogin(
        @Field(ApiConstants.LoginApiHeader.USERNAME) username: String,
        @Field(ApiConstants.LoginApiHeader.PASSWORD) password: String,
        @Field(ApiConstants.LoginApiHeader.TYPE) type: Int,
        @Field(ApiConstants.LoginApiHeader.IDENTITY) identity: String,
        @Field(ApiConstants.LoginApiHeader.TOKEN) token: String = "",
        @Field(ApiConstants.LoginApiHeader.CAPTCHA) captcha: String = ""
    ): Deferred<HttpStatus<Login.UserLogin>>

    @POST(ApiConstants.HttpPath.AUTH_REFRESH)
    @FormUrlEncoded
    fun tokenRefresh(
        @Field(ApiConstants.LoginApiHeader.TOKEN) token: String = ""
    ): Deferred<HttpStatus<Login.TokenRefresh>>

}