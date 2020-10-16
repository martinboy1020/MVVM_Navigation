package com.example.mvvm_navigation.datacenter

import android.content.Context
import com.example.dexlight.datacenter.network.DataCenter
import com.example.dexlight.datacenter.network.RetrofitClient
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.StatusCode
import com.example.mvvm_navigation.datacenter.network.response.RoomInfo
import com.example.mvvm_navigation.datacenter.network.response.UserData

class Repository constructor(val context: Context){

    private val dateCenter = DataCenter()

    companion object{
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

    fun getDeviceList(): MutableList<RoomInfo.Room>{
        /**
         * 這邊可以判斷是否需要從資料中心取的 cache 的資料
         */
        if (this.dateCenter.rooms == null){
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