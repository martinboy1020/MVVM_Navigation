package com.example.mvvm_navigation.datacenter.network

import com.example.mvvm_navigation.datacenter.network.response.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiMethod {

//    @GET(ApiConstants.HttpPath.Users)
//    fun getUsersAsync(): Deferred<List<UserData.User>>
//
//    @GET(ApiConstants.HttpPath.Users)
//    fun getUserFindIndexAsync(
//        @Path("index") index: Int
//    ): Deferred<List<UserData.User>>

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

    @GET(ApiConstants.HttpPath.TG_MATCHES_RECENT)
    fun getTgMatchesRecentAsync(
        @Header("Authorization") token: String = "",
        @Query("timeKey") timeKey: String = "",
        @Query("lang") language: String? = "zh"
    ): Deferred<HttpStatus<MutableList<TgMatchRecent.Recent>>>

    @GET(ApiConstants.HttpPath.STATISTICS_OCCURRENCE_RATE)
    fun getStatisticsOccurrenceRateAsync(
        @Header("Authorization") token: String = "",
        @Query("leagueId") leagueId: String? = "",
        @Query("teamId1") teamId1: String? = "",
        @Query("teamId2") teamId2: String? = "",
        @Query("position") position: Int = 0,
        @Query("condition") condition: String = "",
        @Query("lang") language: String? = "zh"
    ): Deferred<HttpStatus<MatchesStatistics.Data>>

    @GET(ApiConstants.HttpPath.WEB_MATCHES_LIST)
    fun getWebMatchesListAsync(
        @Header("Authorization") token: String = "",
        @Query("date") date: Long,
        @Query("lang") language: String? = "zh"
    ): Deferred<HttpStatus<MatchList.Data>>

    @GET(ApiConstants.HttpPath.TG_MATCHES_BET_INFO)
    fun getMatchesBetInfo(
        @Header("Authorization") token: String = "",
        @Path("matchId") matchId: Int
    ): Deferred<HttpStatus<Home.BetInfo>>

    @GET(ApiConstants.HttpPath.MATCH_STATS)
    fun getMatchStats(
        @Header("Authorization") token: String = "",
        @Path("matchId") matchId: Int
    ): Deferred<HttpStatus<MatchStats.Data>>

    @GET(ApiConstants.HttpPath.SEARCH)
    fun getSearch(
        @Header("Authorization") token: String = "",
        @Query("q") keyword: String = "",
        @Query("lang") language: String = "zh",
        @Query("short") short: Int = 0,
        @Query("content") content: String = ""
    ): Deferred<HttpStatus<Search>>

}