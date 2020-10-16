package com.example.dexlight.datacenter.network

import android.content.Context

class RetrofitClient constructor(private val context: Context){

    companion object{
        fun getInstance(context: Context) = RetrofitClient(context)
    }
}