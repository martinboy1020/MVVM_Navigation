package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class MatchStats {

    data class Data(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int,
        @SerializedName(ApiDataKey.STATS) val stats: MutableList<Stat>,
        @SerializedName(ApiDataKey.ANIMATION) val animationUrl: String = "",
        @SerializedName(ApiDataKey.LIVE_STREAMING) val liveStreamingUrl: String = ""
    )

    data class Stat(
        @SerializedName(ApiDataKey.TYPE) val type: Int,
        @SerializedName(ApiDataKey.HOME) val home: Int,
        @SerializedName(ApiDataKey.AWAY) val away: Int
    )

}