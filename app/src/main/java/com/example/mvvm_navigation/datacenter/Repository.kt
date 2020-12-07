package com.example.mvvm_navigation.datacenter

import android.content.Context
import com.bumptech.glide.Glide
import com.example.dexlight.datacenter.network.DataCenter
import com.example.dexlight.datacenter.network.RetrofitClient
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.StatusCode
import com.example.mvvm_navigation.datacenter.network.response.RoomInfo
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.utils.ReflectViewUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.ExecutionException

class Repository constructor(val context: Context) {

    private val dateCenter = DataCenter()

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
                   bannerItem.bitmap = ReflectViewUtils.reflectionBitmap(Glide.with(context).asBitmap().load(bannerUrlList[i]).submit().get())
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
       return bannerItemList
    }

    fun getDeviceList(): MutableList<RoomInfo.Room> {
        /**
         * 這邊可以判斷是否需要從資料中心取的 cache 的資料
         */
        if (this.dateCenter.rooms == null) {
            /**
             * 可決定是從 api or database 取得資料，並且有需要可在這邊整理資料，將髒亂資料在這邊梳理乾淨後再傳回給正式 app 端
             */
//            val devices1 = mutableListOf<RoomInfo.Device>(
//                RoomInfo.Device(1,"Device1", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(2,"Device2", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(3,"Device3", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(4,"Device4", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(5,"Device5", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(6,"Device6", 1, TypeUtils.DeviceType.DESKLAMP.typeCode)
//            )
//
//            val devices2 = mutableListOf<RoomInfo.Device>(
//                RoomInfo.Device(7,"DeviceA", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(8,"DeviceB", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(9,"DeviceC", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(10,"DeviceD", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(11,"DeviceE", 1, TypeUtils.DeviceType.DESKLAMP.typeCode),
//                RoomInfo.Device(12,"DeviceF", 1, TypeUtils.DeviceType.DESKLAMP.typeCode)
//            )
//            this.dateCenter.rooms = mutableListOf(RoomInfo.Room(1, "ALL", devices1), RoomInfo.Room(2, "Kitchen", devices2))
        }
        return this.dateCenter.rooms!!
    }

}