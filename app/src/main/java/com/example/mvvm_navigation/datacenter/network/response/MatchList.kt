package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class MatchList {

    data class Data(
        @SerializedName(ApiDataKey.AREAS) val areas: MutableList<Area> = mutableListOf(),
        @SerializedName(ApiDataKey.MATCHES) val matches: MutableList<Match> = mutableListOf()
    )

    data class Area(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = "",
        @SerializedName(ApiDataKey.COUNTRIES) val countries: MutableList<Country> = mutableListOf()
    )

    data class Country(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = "",
        @SerializedName(ApiDataKey.LEAGUES) val leagues: MutableList<Leagues> = mutableListOf()
    )

    data class Leagues(
        @SerializedName(ApiDataKey.ID) val id: Int = 0,
        @SerializedName(ApiDataKey.NAME) val name: String = ""
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
        @SerializedName(ApiDataKey.AWAY_SCORE) val awayScore: Int = 0

    )

}