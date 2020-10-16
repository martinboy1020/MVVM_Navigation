package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class RoomInfo{
    data class Room(
        @SerializedName("roomId") var id: Int,
        @SerializedName("roomName") var name: String = "",
        @SerializedName("devices") var deviceList: MutableList<Device>
    )


    data class Device(
        @SerializedName("deviceId") var id: Int,
        @SerializedName("deviceName") var name: String,
        @SerializedName("mode") var mode: Int,
        @SerializedName("type") var type: Int
    )
}