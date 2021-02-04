package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class Member {

    data class Info(
        @SerializedName(ApiDataKey.US_ID) val usId: Int = 0,
        @SerializedName(ApiDataKey.NICK_NAME) val nickName: String = "",
        @SerializedName(ApiDataKey.PROFILE_PHOTO) val profilePhoto: String = "",
        @SerializedName(ApiDataKey.MOBILE) val mobile: String = "",
        @SerializedName(ApiDataKey.EMAIL) val email: String = ""
    )

}