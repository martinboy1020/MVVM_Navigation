package com.example.mvvm_navigation.datacenter.data

import com.google.gson.annotations.SerializedName

data class IncorrectScoreData(
    @SerializedName("type") val type: String = "",
    @SerializedName("name") val name: String = ""
)