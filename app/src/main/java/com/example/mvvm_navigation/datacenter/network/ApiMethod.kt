package com.example.mvvm_navigation.datacenter.network

import com.example.mvvm_navigation.datacenter.network.response.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiMethod {
    @GET(ApiConstants.HttpPath.Users)
    fun getUsersAsync(): Deferred<List<UserData.User>>

    @GET(ApiConstants.HttpPath.Users)
    fun getUserFindIndexAsync(
        @Path("index") index: Int
    ): Deferred<List<UserData.User>>

    @POST(ApiConstants.HttpPath.LOGIN)
    @FormUrlEncoded
    fun userLoginAsync(
        @Field(ApiConstants.LoginApiHeader.USERNAME) username: String,
        @Field(ApiConstants.LoginApiHeader.PASSWORD) password: String,
        @Field(ApiConstants.LoginApiHeader.TYPE) type: Int,
        @Field(ApiConstants.LoginApiHeader.IDENTITY) identity: String,
        @Field(ApiConstants.LoginApiHeader.TOKEN) token: String = "",
        @Field(ApiConstants.LoginApiHeader.CAPTCHA) captcha: String = ""
    ): Deferred<HttpStatus<Login.UserLogin>>

    @POST(ApiConstants.HttpPath.AUTH_REFRESH)
    @FormUrlEncoded
    fun tokenRefreshAsync(
        @Field(ApiConstants.LoginApiHeader.TOKEN) token: String = ""
    ): Deferred<HttpStatus<Login.TokenRefresh>>

    @GET(ApiConstants.HttpPath.WEB_HOME_INFO)
    fun getHomeInfoAsync(
        @Header("Authorization") token: String = ""
    ): Deferred<HttpStatus<Home.WebHomeInfo>>

    @GET(ApiConstants.HttpPath.WEB_MATCHES_LIST)
    fun getWebMatchListAsync(
        @Header("Authorization") token: String = ""
    ): Deferred<HttpStatus<MatchList.Data>>

    @GET(ApiConstants.HttpPath.TG_MATCHES_RECENT)
    fun getTgMatchesRecentAsync(
        @Header("Authorization") token: String = "",
        @Query("timeKey") timeKey: String = "",
        @Query("lang") language: String = ""
    ): Deferred<HttpStatus<MutableList<TgMatchRecent.Recent>>>

}