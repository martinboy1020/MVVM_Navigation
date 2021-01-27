package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class MatchList {

    data class MatchListData(
        @SerializedName(ApiDataKey.RESULT) val result: Boolean,
        @SerializedName(ApiDataKey.TYPE) val type: String,
        @SerializedName(ApiDataKey.DATA) val data: Data
    )

    data class Data(@SerializedName(ApiDataKey.AREAS) val areas: MutableList<Area> = mutableListOf(),
                    @SerializedName(ApiDataKey.MATCHES) var matches: MutableList<Match> = mutableListOf())

    data class Area(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = "",
        @SerializedName(ApiDataKey.COUNTRIES) val countries: MutableList<Country> = mutableListOf(),
        var isCheck: Boolean = false
    )

    data class Country(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = "",
        @SerializedName(ApiDataKey.LEAGUES) val leagues: MutableList<Leagues> = mutableListOf(),
        var areaId: Int = 0,
        var isCheck: Boolean = false
    )

    data class Leagues(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = "",
        val areaId: Int = 0,
        var countryId: Int = 0,
        var isCheck: Boolean = false
    )

    data class Match(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int = 0,
        @SerializedName(ApiDataKey.STATUS) val status: Int = 0,
        @SerializedName(ApiDataKey.OPEN_DATE) val openDate: Long = 0,
        @SerializedName(ApiDataKey.START_TIME) val startTime: Long = 0,
        @SerializedName(ApiDataKey.AREA_ID) val areaId: Int = 0,
        @SerializedName(ApiDataKey.COUNTRY_ID) val country: Int = 0,
        @SerializedName(ApiDataKey.LEAGUE_ID) val leagueId: Int = 0,
        @SerializedName(ApiDataKey.LEAGUE_NAME) val leagueName: String = "",
        @SerializedName(ApiDataKey.LEAGUE_LOGO) val leagueLogo: String = "",
        @SerializedName(ApiDataKey.HOME_ID) val homeId: Int = 0,
        @SerializedName(ApiDataKey.AWAY_ID) val awayId: Int = 0,
        @SerializedName(ApiDataKey.HOME_NAME) val homeName: String = "",
        @SerializedName(ApiDataKey.AWAY_NAME) val awayName: String = "",
        @SerializedName(ApiDataKey.HOME_LOGO) val homeLogo: String = "",
        @SerializedName(ApiDataKey.AWAY_LOGO) val awayLogo: String = "",
        @SerializedName(ApiDataKey.HOME_SCORE) val homeScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_SCORE) val awayScore: Int = 0,
        @SerializedName(ApiDataKey.HOME_HALF_SCORE) val homeHalfScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_HALF_SCORE) val awayHalfScore: Int = 0,
        @SerializedName(ApiDataKey.HOME_YELLOW_CARD) val homeYellowCard: Int = 0,
        @SerializedName(ApiDataKey.AWAY_YELLOW_CARD) val awayYellowCard: Int = 0,
        @SerializedName(ApiDataKey.HOME_RED_CARD) val homeRedCard: Int = 0,
        @SerializedName(ApiDataKey.AWAY_RED_CARD) val awayRedCard: Int = 0,
        @SerializedName(ApiDataKey.HOME_CORNER) val homeCorner: Int = 0,
        @SerializedName(ApiDataKey.AWAY_CORNER) val awayCorner: Int = 0,
        @SerializedName(ApiDataKey.ANIMATION) val animation: String = "",
        @SerializedName(ApiDataKey.LIVE_STREAMING) val liveStreaming: String = "",
        @SerializedName(ApiDataKey.FULL_GAME_TIME) val fullGameTime: Int = 0,
        @SerializedName(ApiDataKey.NEUTRAL) val neutral: Int = 0,
        @SerializedName(ApiDataKey.ROUND) val round: Int = 0,
        @SerializedName(ApiDataKey.BETS_COUNT) val betsCount: Int = 0,
        var isTopOfList: Boolean = false
    )

}