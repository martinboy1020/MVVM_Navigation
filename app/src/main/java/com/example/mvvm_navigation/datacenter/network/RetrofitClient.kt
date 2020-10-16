package com.example.dexlight.datacenter.network

import android.content.Context
import com.example.mvvm_navigation.datacenter.network.ApiMethod
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient constructor(private val context: Context){

    companion object{
        fun getInstance(context: Context) = RetrofitClient(context)
    }

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun getApiMethod(): ApiMethod =
        Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getOkHttpClient(true))
            .build()
            .create(ApiMethod::class.java)

    private fun getOkHttpClient(NoSystemException: Boolean): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
//                    .addInterceptor(Interpolator(this.context, NoSystemException))
            .build()

}