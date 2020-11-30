package com.example.mvvm_navigation.utils

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/** Mutable Live Data */
fun <T: Any?> MutableLiveData<T>.default(value: T) = apply { setValue(value) }

fun <T: Any?> Gson.fromJson(value: String?) = Gson().fromJson<T>(value, object : TypeToken<T>(){}.type)