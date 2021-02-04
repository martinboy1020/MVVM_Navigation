package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class MatchStats {

    data class Data(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int,
        @SerializedName(ApiDataKey.HOME_ID) val homeId: Int,
        @SerializedName(ApiDataKey.HOME_NAME) val homeName: String,
        @SerializedName(ApiDataKey.HOME_LOGO) val homeLogo: String,
        @SerializedName(ApiDataKey.HOME_RANK) val homeRank: String,
        @SerializedName(ApiDataKey.AWAY_ID) val awayId: Int,
        @SerializedName(ApiDataKey.AWAY_NAME) val awayName: String,
        @SerializedName(ApiDataKey.AWAY_LOGO) val awayLogo: String,
        @SerializedName(ApiDataKey.AWAY_RANK) val awayRank: String,
        @SerializedName(ApiDataKey.ANIMATION) val animation: String,
        @SerializedName(ApiDataKey.LIVE_STREAMING) val liveStreaming: String,
        @SerializedName(ApiDataKey.STATS) val stats: MutableList<Stat>,
        @SerializedName(ApiDataKey.ANIMATION) val animationUrl: String = "",
        @SerializedName(ApiDataKey.LIVE_STREAMING) val liveStreamingUrl: String = "",
        @SerializedName(ApiDataKey.RECENT_MATCHES) val recentMatch: RecentMatch,
        @SerializedName(ApiDataKey.FUTURE_MATCHES) val futureMatch: FutureMatch
    )

    data class Stat(
        @SerializedName(ApiDataKey.TYPE) val type: Int,
        @SerializedName(ApiDataKey.HOME) val home: Int,
        @SerializedName(ApiDataKey.AWAY) val away: Int
    )

    data class RecentMatch(
        @SerializedName(ApiDataKey.HOME) val home: MutableList<HomeAway>,
        @SerializedName(ApiDataKey.AWAY) val away: MutableList<HomeAway>
    )

    data class HomeAway(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int,
        @SerializedName(ApiDataKey.RESULT) val result: String
    )

    data class FutureMatch(
        @SerializedName(ApiDataKey.LEAGUE_MATCHES) val leagueMatches: MutableList<Matches>,
        @SerializedName(ApiDataKey.HOME_MATCHES) val homeMatches: MutableList<Matches>,
        @SerializedName(ApiDataKey.AWAY_MATCHES) val awayMatches: MutableList<Matches>
    )

    data class Matches(
        @SerializedName(ApiDataKey.MATCH_ID) val matchId: Int,
        @SerializedName(ApiDataKey.LEAGUE_ID) val leagueId: Int,
        @SerializedName(ApiDataKey.LEAGUE_NAME) val leagueName: String,
        @SerializedName(ApiDataKey.LEAGUE_LOGO) val leagueLogo: String,
        @SerializedName(ApiDataKey.HOME_ID) val homeId: Int,
        @SerializedName(ApiDataKey.HOME_NAME) val homeName: String,
        @SerializedName(ApiDataKey.HOME_LOGO) val homeLogo: String,
        @SerializedName(ApiDataKey.HOME_SCORE) val homeScore: Int,
        @SerializedName(ApiDataKey.AWAY_ID) val awayId: Int,
        @SerializedName(ApiDataKey.AWAY_NAME) val awayName: String,
        @SerializedName(ApiDataKey.AWAY_LOGO) val awayLogo: String,
        @SerializedName(ApiDataKey.AWAY_SCORE) val awayScore: Int,
        @SerializedName(ApiDataKey.OPEN_DATE) val openDate: Long,
        @SerializedName(ApiDataKey.STATUS) val status: Int
    )

}