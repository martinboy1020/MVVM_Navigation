package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class TgMatchRecent {

    data class Recent(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int = 0,
        @SerializedName(ApiDataKey.TG_STATUS) val tgStatus: Int = 0,
        @SerializedName(ApiDataKey.HOME_ID) val homeId: Int = 0,
        @SerializedName(ApiDataKey.AWAY_ID) val awayId: Int = 0,
        @SerializedName(ApiDataKey.HOME) val home: String = "",
        @SerializedName(ApiDataKey.AWAY) val away: String = "",
        @SerializedName(ApiDataKey.HOME_LOGO) val homeLogo: String = "",
        @SerializedName(ApiDataKey.AWAY_LOGO) val awayLogo: String = "",
        @SerializedName(ApiDataKey.LEAGUE_ID) val leagueId: Int = 0,
        @SerializedName(ApiDataKey.LEAGUE) val league: String = "",
        @SerializedName(ApiDataKey.LEAGUE_SHORT_NAME_EN) val leagueShortNameEn: String = "",
        @SerializedName(ApiDataKey.LEAGUE_LOGO) val leagueLogo: String = "",
        @SerializedName(ApiDataKey.OPEN_DATE) val openDate: Long = 0,
        @SerializedName(ApiDataKey.BETS_COUNT) val betsCount: Int = 0,
        @SerializedName(ApiDataKey.MAX_SCORE) val maxScore: String = "",
        @SerializedName(ApiDataKey.MIN_SCORE) val minScore: String = "",
        @SerializedName(ApiDataKey.MAX_VALUE) val maxValue: String = "",
        @SerializedName(ApiDataKey.MIN_VALUE) val minValue: String = "",
        val isTopOfList: Boolean = false
    )

}