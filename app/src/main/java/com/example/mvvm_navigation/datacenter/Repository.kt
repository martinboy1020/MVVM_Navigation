package com.example.mvvm_navigation.datacenter

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.DataCenter
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.RetrofitClient
import com.example.mvvm_navigation.datacenter.network.StatusCode
import com.example.mvvm_navigation.datacenter.network.response.*
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.utils.ReflectViewUtils
import java.util.*
import java.util.concurrent.ExecutionException

class Repository constructor(val context: Context) {

    private val dataCenter = DataCenter()

    companion object {
        fun getInstance(context: Context) = Repository(context)
    }

    fun getUserSharaPreference(): UserSharePreferences {
        return UserSharePreferences(context)
    }

//    suspend fun getUser(): HttpResult<List<UserData.User>> =
//        try {
//            val response =
//                RetrofitClient.getInstance(context).getApiMethod().getUsersAsync().await()
//            if (!response.isNullOrEmpty()) HttpResult.onSuccess(response)
//            else HttpResult.onError((-1000).toString(), "List is Empty")
//        } catch (e: Throwable) {
//            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
//        }

    suspend fun userLogin(
        username: String,
        password: String,
        type: Int
    ): HttpResult<HttpStatus<Login.UserLogin>> =
        try {
            val uuid = UUID.randomUUID().toString()
            val response = RetrofitClient.getInstance(context).getApiMethod()
                .userLoginAsync(username, password, type, uuid).await()
            if (response.statusCode == "0") {
                UserSharePreferences(context).userToken = response.payload.token
                HttpResult.onSuccess(response)
            } else {
                HttpResult.onError(
                    response.statusCode,
                    response.message
                )
            }
        } catch (e: Throwable) {
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    suspend fun refreshUserToken(token: String): HttpResult<HttpStatus<Login.TokenRefresh>> =
        try {
            val response =
                RetrofitClient.getInstance(context).getApiMethod().tokenRefreshAsync(token).await()
            if (response.statusCode == "0") {
                UserSharePreferences(context).userToken = response.payload.token
                HttpResult.onSuccess(response)
            } else HttpResult.onError(
                response.statusCode,
                response.message
            )
        } catch (e: Throwable) {
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    suspend fun getHomeInfo(): HttpResult<HttpStatus<Home.WebHomeInfo>> =
        try {
            val response =
                RetrofitClient.getInstance(context).getApiMethod()
                    .getHomeInfoAsync("Bearer " + UserSharePreferences(context).userToken).await()
            if (response.statusCode == "0") HttpResult.onSuccess(response) else HttpResult.onError(
                response.statusCode,
                response.message
            )
        } catch (e: Throwable) {
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    suspend fun getTgMatchesRecent(timeKey: String): HttpResult<HttpStatus<MutableList<TgMatchRecent.Recent>>> =
        try {
            val response =
                RetrofitClient.getInstance(context).getApiMethod().getTgMatchesRecentAsync(
                    "Bearer " + UserSharePreferences(context).userToken,
                    timeKey
                ).await()
            if (response.statusCode == "0") HttpResult.onSuccess(response) else HttpResult.onError(
                response.statusCode,
                response.message
            )
        } catch (e: Throwable) {
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    fun getBannerList(homeInfo: Home.WebHomeInfo): MutableList<BannerItem> {
        if (!homeInfo.banners.isNullOrEmpty()) {
            val bannerItemList: MutableList<BannerItem> = ArrayList()
            for (i in homeInfo.banners.indices) {
                val bannerItem = BannerItem()
                bannerItem.title = homeInfo.banners[i].titles
                bannerItem.linkUrl = homeInfo.banners[i].linkUrl
                bannerItem.imgUrl = homeInfo.banners[i].imgUrl
                bannerItem.type = homeInfo.banners[i].type
                try {
                    if (homeInfo.banners[i].imgUrl != "" && !homeInfo.banners[i].imgUrl.contains(".mp4")) {
//                        bannerItem.bitmap = ReflectViewUtils.reflectionBitmap(
//                            Glide.with(context).asBitmap().load(bannerUrlList[i]).submit().get()
//                        )
                        bannerItem.bitmap = ReflectViewUtils.ratioBitmap(
                            Glide.with(context).asBitmap().load(homeInfo.banners[i].imgUrl).submit()
                                .get()
                        )
                    } else {
                        bannerItem.bitmap = null
                    }
                } catch (e: ExecutionException) {
                    bannerItem.bitmap = null
                } catch (e: InterruptedException) {
                    bannerItem.bitmap = null
                }
                bannerItemList.add(bannerItem)
            }
            dataCenter.bannerList = bannerItemList
            return bannerItemList
        } else {
            return dataCenter.bannerList
        }
    }

    suspend fun getMatchStatistics(
        leagueId: Int?,
        homeId: Int?,
        awayId: Int?,
        position: Int = 0,
        condition: String = ""
    ): HttpResult<HttpStatus<MatchesStatistics.Data>> =
        try {
            val strLeagueId = leagueId?.toString() ?: ""
            val strHomeId = homeId?.toString() ?: ""
            val strAwayId = awayId?.toString() ?: ""
            val response =
                RetrofitClient.getInstance(context).getApiMethod().getStatisticsOccurrenceRateAsync(
                    "Bearer " + UserSharePreferences(context).userToken,
                    strLeagueId, strHomeId, strAwayId, position, condition
                ).await()
            if (response.statusCode == "0") HttpResult.onSuccess(response) else HttpResult.onError(
                response.statusCode,
                response.message
            )
        } catch (e: Throwable) {
            Log.d("tag12345", "getMatchStatistics e.message: ${e.message}")
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    fun getBetList(): MutableList<BetData> {
        val betList = mutableListOf<BetData>()
        val betData1 = BetData(0, "1 - 2", true)
        val betData2 = BetData(1, "0 - 1", true)
        val betData3 = BetData(2, "1 - 0", true)
        val betData4 = BetData(3, "1 - 3", false)
        betList.add(betData1)
        betList.add(betData2)
        betList.add(betData3)
        betList.add(betData4)
        dataCenter.betList = betList
        return betList
    }

    fun getRecentMatchCondition(): MutableList<RecentMatchCondition> {
        val recentMatchConditionList = mutableListOf<RecentMatchCondition>()
        val recentMatchCondition1 = RecentMatchCondition("近50場", "")
        val recentMatchCondition2 = RecentMatchCondition("近30場", "")
        val recentMatchCondition3 = RecentMatchCondition("近10場", "")
        val recentMatchCondition4 = RecentMatchCondition("近5場", "")
        val recentMatchCondition5 = RecentMatchCondition("2020-2019", "")
        recentMatchConditionList.add(recentMatchCondition1)
        recentMatchConditionList.add(recentMatchCondition2)
        recentMatchConditionList.add(recentMatchCondition3)
        recentMatchConditionList.add(recentMatchCondition4)
        recentMatchConditionList.add(recentMatchCondition5)
        return recentMatchConditionList
    }

    suspend fun getWebMatchList(date: Long): HttpResult<HttpStatus<MatchList.Data>> =
        try {
            val response =
                RetrofitClient.getInstance(context).getApiMethod().getWebMatchesListAsync(
                    "Bearer " + UserSharePreferences(context).userToken,
                    date
                ).await()
            if (response.statusCode == "0") {
                dataCenter.matchList = response.payload.matches
                HttpResult.onSuccess(response)
            } else HttpResult.onError(
                response.statusCode,
                response.message
            )
        } catch (e: Throwable) {
            Log.d("tag12345", "getWebMatchList e.message: ${e.message}")
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    fun refreshTopListMatch(data: MatchList.Match): MutableList<MatchList.Match> {

        var isExitsInTop = false
        var isExitsInTopPosition = -1
        for (i in dataCenter.matchTopList.indices) {
            if (data.matchId == dataCenter.matchTopList[i].matchId) {
                isExitsInTop = true
                isExitsInTopPosition = i
                break
            }
        }

        if (isExitsInTop) {
            dataCenter.matchTopList.removeAt(isExitsInTopPosition)
            data.isTopOfList = false
            dataCenter.matchList.add(data)
        } else {
            data.isTopOfList = true
            dataCenter.matchTopList.add(0, data)
            dataCenter.matchTopList.sortWith(compareBy({ it.openDate }, { it.matchId }))
            dataCenter.matchList.remove(data)
        }
        dataCenter.matchList.sortWith(compareBy({ it.openDate }, { it.matchId }))
        dataCenter.matchAllList.clear()
        dataCenter.matchAllList = mutableListOf()
        dataCenter.matchAllList.addAll(dataCenter.matchTopList)
        dataCenter.matchAllList.addAll(dataCenter.matchList)

        return dataCenter.matchAllList

    }

}