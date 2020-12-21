package com.example.mvvm_navigation.datacenter.network

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitClient constructor(private val context: Context) {

    companion object {
        fun getInstance(context: Context) = RetrofitClient(context)
    }

    //    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val BASE_URL_DEV = "https://usdev.k33uc.com/api/"
    private val BASE_URL_PROD = "https://us.k33uc.com/api/"
    private val isShowLog = true

    // 攔截器 用於查看API回傳的Log時使用 Log關鍵字: OkHttp
    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun getApiMethod(): ApiMethod =
        Retrofit.Builder()
            .baseUrl(this.BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getOkHttpClient(isShowLog))
            .build()
            .create(ApiMethod::class.java)

    private fun getOkHttpClient(isShowLog: Boolean): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS)
        okHttpClient.readTimeout(10, TimeUnit.SECONDS)
        if(isShowLog) okHttpClient.addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

}