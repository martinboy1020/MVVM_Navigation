package com.example.mvvm_navigation.datacenter

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.mvvm_navigation.MainApplication
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.IncorrectScoreData
import com.example.mvvm_navigation.datacenter.data.RecentMatchCondition
import com.example.mvvm_navigation.datacenter.network.DataCenter
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.RetrofitClient
import com.example.mvvm_navigation.datacenter.network.StatusCode
import com.example.mvvm_navigation.datacenter.network.response.*
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.ui.main.MainActivity
import com.example.mvvm_navigation.ui.match.matchlist.MatchListFragment
import com.example.mvvm_navigation.utils.GameStatusUtils
import com.example.mvvm_navigation.utils.GetAssetsUtils
import com.example.mvvm_navigation.utils.LogUtils
import com.example.mvvm_navigation.utils.ReflectViewUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            if (response.statusCode == "0") {
                val seasons = response.payload.seasons
                val conditionValueList =
                    context.resources.getStringArray(R.array.recentConditionValue)
                val conditionStringList =
                    context.resources.getStringArray(R.array.recentConditionString)
                val newSeasonList = mutableListOf<MatchesStatistics.Season>()
                for (i in conditionValueList.indices) {
                    val season =
                        MatchesStatistics.Season(conditionValueList[i], conditionStringList[i])
                    newSeasonList.add(season)
                }
                newSeasonList.addAll(seasons)
                val all = MatchesStatistics.Season("all", "歷史總計")
                newSeasonList.add(all)
                response.payload.seasons = newSeasonList
                HttpResult.onSuccess(response)
            } else {
                HttpResult.onError(
                    response.statusCode,
                    response.message
                )
            }
        } catch (e: Throwable) {
            LogUtils.d("tag12345", "getMatchStatistics e.message: ${e.message}")
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

    suspend fun getMatchList(date: Long, status: Int): HttpResult<HttpStatus<MatchList.Data>> =
        try {
            LogUtils.d("tag123456789", "getWebMatchList date: $date")
            val response =
                RetrofitClient.getInstance(context).getApiMethod().getMatchesListAsync(
                    "Bearer " + UserSharePreferences(context).userToken,
                    date
                ).await()
            if (response.statusCode == "0") {
                getAreaList(response.payload)
                val filterList = filterList(response.payload.matches, status)
                when (status) {
                    MatchListFragment.MATCH_UNOPEN -> {
                        dataCenter.matchUnOpenList = filterList
                    }
                    MatchListFragment.MATCH_ENDING -> {
                        dataCenter.matchEndingList = filterList
                    }
                    else -> {
                        dataCenter.matchIngList = filterList
                    }
                }
                response.payload.matches = filterList
                HttpResult.onSuccess(response)
            } else {
                if (response.statusCode == "1005") {
                    restartApp()
                }
                HttpResult.onError(
                    response.statusCode,
                    response.message
                )
            }
        } catch (e: Throwable) {
            LogUtils.d("tag123456789", "getWebMatchList e.message: ${e.message}")
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    private fun getAreaList(list: MatchList.Data) {
        val areaList = list.areas
        dataCenter.filterAreaList = areaList
    }

    private fun filterList(
        list: MutableList<MatchList.Match>,
        pageType: Int
    ): MutableList<MatchList.Match> {
        val filterList = mutableListOf<MatchList.Match>()
        for (i in list.indices) {
            when (pageType) {

                MatchListFragment.MATCH_UNOPEN -> {
                    if (GameStatusUtils.MatchStatus.checkGameMatchIsUnOpen(list[i].status)
                        && !GameStatusUtils.MatchStatus.checkMatchTransaction(list[i].status)
                    ) {
                        filterList.add(list[i])
                        LogUtils.d("tag123456789", "Unopen: " + list[i])
                    }
                }

                MatchListFragment.MATCH_ENDING -> {
                    if (list[i].status == GameStatusUtils.MatchStatus.ENDING) {
                        filterList.add(list[i])
                        LogUtils.d("tag123456789", "Ending: " + list[i])
                    }
                }

                else -> {
                    if (GameStatusUtils.MatchStatus.checkGameMatchIsStarted(list[i].status)) {
                        filterList.add(list[i])
                        LogUtils.d("tag123456789", "Ing: " + list[i])
                    }
                }

            }
        }
        return filterList
    }

    fun getFilterAreaList(): MutableList<MatchList.Area> {
        val responseString = GetAssetsUtils.getJsonDataFromAsset(context, "matchlist.json")
        if (responseString != null) {
            val matchListType = object : TypeToken<HttpStatus<MatchList.Data>>() {}.type
            val data: HttpStatus<MatchList.Data> = Gson().fromJson(responseString, matchListType)
            getAreaList(data.payload)
        }
        return dataCenter.filterAreaList
    }

    fun getFilterCountryList(): MutableList<MatchList.Country> {
        return dataCenter.filterCountryList
    }

    fun getFilterLeagueList(): MutableList<MatchList.Leagues> {
        return dataCenter.filterLeagueList
    }

    fun getTestMatchListData(): HttpStatus<MatchList.Data>? {
        val responseString = GetAssetsUtils.getJsonDataFromAsset(context, "matchlist.json")
        if (responseString != null) {
            val matchListType = object : TypeToken<HttpStatus<MatchList.Data>>() {}.type
            val data: HttpStatus<MatchList.Data> = Gson().fromJson(responseString, matchListType)
            dataCenter.matchAllList = data.payload.matches
            return Gson().fromJson(responseString, matchListType)
        }
        return null
    }

    fun refreshTopListMatch(data: MatchList.Match, pageType: Int): MutableList<MatchList.Match> {

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
            dataCenter.matchAllList.clear()
            dataCenter.matchAllList.addAll(dataCenter.matchTopList)
            when (pageType) {
                MatchListFragment.MATCH_UNOPEN -> {
                    dataCenter.matchUnOpenList.add(data)
                    dataCenter.matchUnOpenList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.addAll(dataCenter.matchUnOpenList)
                }
                MatchListFragment.MATCH_ENDING -> {
                    dataCenter.matchEndingList.add(data)
                    dataCenter.matchEndingList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.addAll(dataCenter.matchEndingList)
                }
                else -> {
                    dataCenter.matchIngList.add(data)
                    dataCenter.matchIngList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.addAll(dataCenter.matchIngList)
                }
            }
        } else {
            data.isTopOfList = true
            dataCenter.matchTopList.add(0, data)
            dataCenter.matchTopList.sortWith(compareBy({ it.openDate }, { it.matchId }))
            when (pageType) {
                MatchListFragment.MATCH_UNOPEN -> {
                    dataCenter.matchUnOpenList.remove(data)
                    dataCenter.matchUnOpenList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.clear()
                    dataCenter.matchAllList.addAll(dataCenter.matchTopList)
                    dataCenter.matchAllList.addAll(dataCenter.matchUnOpenList)
                }
                MatchListFragment.MATCH_ENDING -> {
                    dataCenter.matchEndingList.remove(data)
                    dataCenter.matchEndingList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.clear()
                    dataCenter.matchAllList.addAll(dataCenter.matchTopList)
                    dataCenter.matchAllList.addAll(dataCenter.matchEndingList)
                }
                else -> {
                    dataCenter.matchIngList.remove(data)
                    dataCenter.matchIngList.sortWith(compareBy({ it.openDate }, { it.matchId }))
                    dataCenter.matchAllList.clear()
                    dataCenter.matchAllList.addAll(dataCenter.matchTopList)
                    dataCenter.matchAllList.addAll(dataCenter.matchIngList)
                }
            }
        }


//        for (i in dataCenter.matchList.indices) {
//            if (data.matchId == dataCenter.matchList[i].matchId) {
//                dataCenter.matchList.removeAt(i)
//                break
//            }
//        }
//        dataCenter.matchAllList.clear()
//        dataCenter.matchAllList = dataCenter.matchList

        return dataCenter.matchAllList

    }

    fun getIncorrectDataList(): MutableList<IncorrectScoreData>? {
        LogUtils.d("tag123456789", "getIncorrectDataList")
        val responseString = GetAssetsUtils.getJsonDataFromAsset(context, "incorrect_score.json")
        LogUtils.d("tag123456789", "getIncorrectDataList responseString: $responseString")
        if (responseString != null) {
            val incorrectScoreDataType =
                object : TypeToken<MutableList<IncorrectScoreData>>() {}.type
            return Gson().fromJson(responseString, incorrectScoreDataType)
        }
        return null
    }

    suspend fun getMatchDetail(matchId: Int): HttpResult<HttpStatus<MatchDetail.Data>> =
        try {
            val response = RetrofitClient.getInstance(this.context).getApiMethod()
                .getMatchDetailAsync("Bearer " + UserSharePreferences(context).userToken, matchId)
                .await()
            if (response.statusCode == "0") {
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

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context, intent, null)
        val currentActivity = MainApplication().getCurrentActivity()
        if (currentActivity != null) ActivityCompat.finishAffinity(currentActivity)
    }

}