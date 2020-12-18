package com.example.mvvm_navigation.datacenter.network.response

import com.example.mvvm_navigation.datacenter.network.response.ApiDataKey
import com.google.gson.annotations.SerializedName

class Login {

    data class UserLogin(
        @SerializedName(ApiDataKey.US_ID) val usId: Int = 0,
        @SerializedName(ApiDataKey.NICK_NAME) val nickname: String = "",
        @SerializedName(ApiDataKey.PROFILE_PHOTO) val profilePhoto: String = "",
        @SerializedName(ApiDataKey.LEVEL) val level: Int = 0,
        @SerializedName(ApiDataKey.SCORE) val score: String = "",
        @SerializedName(ApiDataKey.AMOUNT) val amount: String = "",
        @SerializedName(ApiDataKey.MOBILE) val mobile: String = "",
        @SerializedName(ApiDataKey.EMAIL) val email: String = "",
        @SerializedName(ApiDataKey.NOTIFICATIONS) val notifications: Int = 0,
        @SerializedName(ApiDataKey.MISSIONS) val missions: Int = 0,
        @SerializedName(ApiDataKey.WEIXIN) val weixin: Int = 0,
        @SerializedName(ApiDataKey.QQ) val qq: Int = 0,
        @SerializedName(ApiDataKey.WEIBO) val weibo: Int = 0,
        @SerializedName(ApiDataKey.TOKEN) val token: String = ""
    )

    data class TokenRefresh(@SerializedName(ApiDataKey.TOKEN) val token: String = "")

}