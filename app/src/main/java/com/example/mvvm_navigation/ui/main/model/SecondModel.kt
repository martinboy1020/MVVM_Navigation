package com.example.mvvm_navigation.ui.main.model

import com.example.mvvm_navigation.base.BaseModel
import com.example.mvvm_navigation.datacenter.Repository
import com.example.mvvm_navigation.datacenter.network.HttpResult
import com.example.mvvm_navigation.datacenter.network.response.UserData
import com.example.mvvm_navigation.ui.main.vm.second.SecondContract

class SecondModel constructor(val repository: Repository): BaseModel(), SecondContract.ModelImpl{
    companion object{
        fun getInstance(repository: Repository) = SecondModel(repository)
    }

    override suspend fun getUser(): HttpResult<List<UserData.User>> {
        return when(val response = this@SecondModel.repository.getUser()){
            is HttpResult.onSuccess -> HttpResult.onSuccess(response.data)
            is HttpResult.onError -> HttpResult.onError(response.errorCode, response.errorMsg)
        }
    }

//    override fun getAllDevice(): MutableList<RoomInfo.Room> {

//        val response = this.repository.getDeviceList()
//        return DataFilter().filterRooms(response)

//        val devices1 = mutableListOf<Device>(
//            Device("Device1", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("Device2", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("Device3", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("Device4", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("Device5", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("Device6", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false)
//        )
//
//        val devices2 = mutableListOf<Device>(
//            Device("DeviceA", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("DeviceB", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("DeviceC", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("DeviceD", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("DeviceE", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false),
//            Device("DeviceF", 1, TypeUtils.DeviceType.DESKLAMP.typeCode, false)
//        )
//        return mutableListOf(Room("ALL", devices1), Room("Kitchen", devices2))
//    }
}