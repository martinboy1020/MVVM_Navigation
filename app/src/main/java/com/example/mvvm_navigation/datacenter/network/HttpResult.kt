package com.example.mvvm_navigation.datacenter.network

sealed class HttpResult <out T: Any>{
    class onSuccess<out T: Any>(val data: T): HttpResult<T>()
    class onError(val errorCode: String, val errorMsg: String?): HttpResult<Nothing>()
}