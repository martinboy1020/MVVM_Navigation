package com.example.dexlight.datacenter.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseStatus<T>(
    @SerializedName("statusCode")
    @Expose
    var statusCode: String,
    @SerializedName("message")
    @Expose
    var message: String = "",
    @SerializedName("payload")
    @Expose
    var payload: T
)