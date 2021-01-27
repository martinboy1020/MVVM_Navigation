package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class MatchDetail {

    data class Data(
        @SerializedName(ApiDataKey.MATCH_ID) var matchId: Int = 0,
        @SerializedName(ApiDataKey.LEAGUE_NAME) var leagueName: String = "",
        @SerializedName(ApiDataKey.ROUND) var round: Int = 0,
        @SerializedName(ApiDataKey.HOME_ID) var homeId: Int = 0,
        @SerializedName(ApiDataKey.HOME_NAME) var homeName: String = "",
        @SerializedName(ApiDataKey.HOME_LOGO) var homeLogo: String = "",
        @SerializedName(ApiDataKey.HOME_SCORE) var homeScore: Int = 0,
        @SerializedName(ApiDataKey.HOME_HALF_SCORE) var homeHalfScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_ID) var awayId: Int = 0,
        @SerializedName(ApiDataKey.AWAY_NAME) var awayName: String = "",
        @SerializedName(ApiDataKey.AWAY_LOGO) var awayLogo: String = "",
        @SerializedName(ApiDataKey.AWAY_SCORE) var awayScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_HALF_SCORE) var awayHalfScore: Int = 0,
        @SerializedName(ApiDataKey.OPEN_DATE) var openDate: Long = 0,
        @SerializedName(ApiDataKey.STATUS) var status: Int = 0,
        @SerializedName(ApiDataKey.START_TIME) var startTime: Long = 0,
        @SerializedName(ApiDataKey.ANIMATION) var animation: String = "",
        @SerializedName(ApiDataKey.LIVE_STREAMING) var liveStreaming: String = "",
        @SerializedName(ApiDataKey.STATS) var stats: MutableList<Stats>,
        @SerializedName(ApiDataKey.T_LIVE) var tlive: MutableList<TLive>,
        @SerializedName(ApiDataKey.INCIDENTS) var incidents: MutableList<Incidents>,
        @SerializedName(ApiDataKey.BATTLE_RECORD) var battleRecord: BattleRecordRecentMatches,
        @SerializedName(ApiDataKey.RECENT_MATCHES) var recentMatches: BattleRecordRecentMatches
    )

    data class Stats(
        @SerializedName(ApiDataKey.TYPE) var type: Int = 0,
        @SerializedName(ApiDataKey.HOME) var home: Int = 0,
        @SerializedName(ApiDataKey.AWAY) var away: Int = 0
    )

    data class TLive(
        @SerializedName(ApiDataKey.DATA) var data: String = "",
        @SerializedName(ApiDataKey.POSITION) var position: Int = 0,
        @SerializedName(ApiDataKey.TYPE) var type: Int = 0,
        @SerializedName(ApiDataKey.TIME) var time: String = ""
    )

    data class Incidents(
        @SerializedName(ApiDataKey.TYPE) var type: Int = 0,
        @SerializedName(ApiDataKey.POSITION) var position: Int = 0,
        @SerializedName(ApiDataKey.TIME) var time: String = "",
        @SerializedName(ApiDataKey.PLAYER_ID) var playerId: Int = 0,
        @SerializedName(ApiDataKey.PLAYER_NAME) var playerName: String = "",
        @SerializedName(ApiDataKey.ASSIST_1_ID) var assist1Id: Int = 0,
        @SerializedName(ApiDataKey.ASSIST_1_NAME) var assist1Name: String = "",
        @SerializedName(ApiDataKey.ASSIST_2_ID) var assist2Id: Int = 0,
        @SerializedName(ApiDataKey.ASSIST_2_NAME) var assist2Name: String = "",
        @SerializedName(ApiDataKey.IN_PLAYER_ID) var inPlayerId: Int = 0,
        @SerializedName(ApiDataKey.IN_PLAYER_NAME) var inPlayerName: String = "",
        @SerializedName(ApiDataKey.OUT_PLAYER_ID) var outPlayerId: Int = 0,
        @SerializedName(ApiDataKey.OUT_PLAYER_NAME) var outPlayName: String = "",
        @SerializedName(ApiDataKey.HOME_SCORE) var homeScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_SCORE) var awayScore: Int = 0
    )

    data class BattleRecordRecentMatches(
        @SerializedName(ApiDataKey.HOME) var home: HomeAway,
        @SerializedName(ApiDataKey.AWAY) var away: HomeAway
    )

    data class HomeAway(
        @SerializedName(ApiDataKey.WIN) var win: Int = 0,
        @SerializedName(ApiDataKey.LOST) var lost: Int = 0,
        @SerializedName(ApiDataKey.TIE) var tie: Int = 0,
        @SerializedName(ApiDataKey.TOTAL) var total: Int = 0,
        @SerializedName(ApiDataKey.MATCHES) var matches: MutableList<Matches>,
        @SerializedName(ApiDataKey.LEAGUES) var leagues: LeaguesTeams,
        @SerializedName(ApiDataKey.TEAMS) var teams: LeaguesTeams
    )

    data class Matches(
        @SerializedName(ApiDataKey.MATCH_ID) var matchId: Int = 0,
        @SerializedName(ApiDataKey.LEAGUE_ID) var leagueId: Int = 0,
        @SerializedName(ApiDataKey.OPEN_DATE) var openDate: Long = 0,
        @SerializedName(ApiDataKey.HOME_ID) var homeId: Int = 0,
        @SerializedName(ApiDataKey.AWAY_ID) var awayId: Int = 0,
        @SerializedName(ApiDataKey.HOME_SCORE) var homeScore: Int = 0,
        @SerializedName(ApiDataKey.AWAY_SCORE) var awayScore: Int = 0
    )

    data class LeaguesTeams(
        @SerializedName(ApiDataKey.LEAGUE_ID) var leagueId: Int = 0,
        @SerializedName(ApiDataKey.NAME) var name: String = "",
        @SerializedName(ApiDataKey.SHORT_NAME) var shortName: String = "",
        @SerializedName(ApiDataKey.LOGO) var logo: Int = 0
    )

}