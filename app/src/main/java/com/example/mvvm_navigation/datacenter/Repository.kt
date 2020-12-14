package com.example.mvvm_navigation.datacenter

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mvvm_navigation.datacenter.network.DataCenter
import com.example.dexlight.datacenter.network.RetrofitClient
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.data.BetData
import com.example.mvvm_navigation.datacenter.data.MatchListItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.StatusCode
import com.example.mvvm_navigation.datacenter.network.response.RoomInfo
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.utils.ReflectViewUtils
import java.util.*
import java.util.concurrent.ExecutionException

class Repository constructor(val context: Context) {

    private val dataCenter = DataCenter()

    companion object {
        fun getInstance(context: Context) = Repository(context)
    }

    suspend fun getUser(): HttpResult<List<UserData.User>> =
        try {
            val response = RetrofitClient.getInstance(context).getApiMethod().getUsers().await()
            if (!response.isNullOrEmpty()) HttpResult.onSuccess(response)
            else HttpResult.onError((-1000).toString(), "List is Empty")
        } catch (e: Throwable) {
            HttpResult.onError(StatusCode.HTTP.BadRequest.toString(), e.message)
        }

    fun getBannerList(): MutableList<BannerItem> {
        if (dataCenter.bannerList.isNullOrEmpty()) {
            val bannerItemList: MutableList<BannerItem> = ArrayList()
            val bannerUrlList = ArrayList<String>()
            bannerUrlList.add("http://banner.lkwlp.cn/uscore/banners/5e588782d948a.mp4")
            bannerUrlList.add("https://banner.lkwlp.cn/uscore/banners/5e3c014298134.jpg")
            bannerUrlList.add("https://banner.lkwlp.cn/uscore/banners/5e3c00e6a394c.jpg")
            bannerUrlList.add("https://banner.lkwlp.cn/uscore/banners/5e3c0122e9665.jpg")
            bannerUrlList.add("https://banner.lkwlp.cn/uscore/banners/5e3a85794147a.jpg")
            for (i in bannerUrlList.indices) {
                val bannerItem = BannerItem()
                bannerItem.imgUrl = bannerUrlList[i]
                try {
                    if (bannerUrlList[i] != "" && !bannerUrlList[i].contains(".mp4")) {
//                        bannerItem.bitmap = ReflectViewUtils.reflectionBitmap(
//                            Glide.with(context).asBitmap().load(bannerUrlList[i]).submit().get()
//                        )
                        bannerItem.bitmap = ReflectViewUtils.ratioBitmap(
                            Glide.with(context).asBitmap().load(bannerUrlList[i]).submit().get()
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
            return dataCenter.bannerList!!
        }
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

    fun getMatchList(): MutableList<MatchListItem> {

        val matchList = mutableListOf<MatchListItem>()

        for (i in 0..10) {
            val matchListItem = MatchListItem()
            matchListItem.matchId = i
            matchListItem.homeName = "Home $i"
            matchListItem.awayName = "Away $i"
            matchList.add(matchListItem)
        }

        dataCenter.matchList = matchList

        return matchList
    }

    fun refreshTopListMatch(data: MatchListItem): MutableList<MatchListItem> {

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
            dataCenter.matchTopList.sortWith(compareBy { it.matchId })
            dataCenter.matchList.remove(data)
        }
        dataCenter.matchList.sortWith(compareBy { it.matchId })
        dataCenter.matchAllList.clear()
        dataCenter.matchAllList.addAll(dataCenter.matchTopList)
        dataCenter.matchAllList.addAll(dataCenter.matchList)

        return dataCenter.matchAllList

    }

}