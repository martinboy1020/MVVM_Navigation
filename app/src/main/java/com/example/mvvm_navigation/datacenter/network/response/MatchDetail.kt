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
        @SerializedName(ApiDataKey.LINE_UP) var lineUp: LineUp,
        @SerializedName(ApiDataKey.BATTLE_RECORD) var battleRecord: BattleRecordRecentMatches,
        @SerializedName(ApiDataKey.RECENT_MATCHES) var recentMatches: BattleRecordRecentMatches,
        @SerializedName(ApiDataKey.FUTURE_MATCHES) var futureMatch: FutureMatch,
        @SerializedName(ApiDataKey.TG_ODDS) var tgOdds: TgOdds,
        @SerializedName(ApiDataKey.ODDS) var odds: Odds
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

    data class LineUp(
        @SerializedName(ApiDataKey.HOME_MANAGER) val homeManager: String = "",
        @SerializedName(ApiDataKey.AWAY_MANAGER) val awayManager: String = "",
        @SerializedName(ApiDataKey.HOME_FORMATION) val homeFormation: String = "",
        @SerializedName(ApiDataKey.AWAY_FORMATION) val awayFormation: String = "",
        @SerializedName(ApiDataKey.HOME) val home: LineUpHomeAway,
        @SerializedName(ApiDataKey.AWAY) val away: LineUpHomeAway
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

    data class LineUpHomeAway(
        @SerializedName(ApiDataKey.ID) var id: Int = 0,
        @SerializedName(ApiDataKey.FIRST) var first: Int = 0,
        @SerializedName(ApiDataKey.NAME) var name: String = "",
        @SerializedName(ApiDataKey.SHIRT_NUMBER) var shirtNumber: Int = 0,
        @SerializedName(ApiDataKey.POSITION) var position: Int = 0,
        @SerializedName(ApiDataKey.POSITION_X) var x: Int = 0,
        @SerializedName(ApiDataKey.POSITION_Y) var y: Int = 0,
        @SerializedName(ApiDataKey.INCIDENTS) var incidents: MutableList<Incidents>
    )

    data class LeaguesTeams(
        @SerializedName(ApiDataKey.LEAGUE_ID) var leagueId: Int = 0,
        @SerializedName(ApiDataKey.NAME) var name: String = "",
        @SerializedName(ApiDataKey.SHORT_NAME) var shortName: String = "",
        @SerializedName(ApiDataKey.LOGO) var logo: Int = 0
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

    data class TgOdds(
        @SerializedName(ApiDataKey.TRANSACTION) val transaction: String,
        @SerializedName(ApiDataKey.PROFIT) val profit: String,
        @SerializedName(ApiDataKey.BET_MONEY) val betMoney: String,
        @SerializedName(ApiDataKey.IS_POPULAR) val isPopular: Int,
        @SerializedName(ApiDataKey.IS_SOLD_OUT) val isSoldOut: Int
    )

    data class Odds(
        @SerializedName(ApiDataKey.ASIA) val asia: OddDetail,
        @SerializedName(ApiDataKey.EU) val eu: OddDetail,
        @SerializedName(ApiDataKey.BS) val bs: OddDetail,
        @SerializedName(ApiDataKey.CR) val cr: OddDetail
    )

    data class OddDetail(
        @SerializedName(ApiDataKey.COMPANY_ID) val companyId: Int,
        @SerializedName(ApiDataKey.COMPANY_NAME) val companyName: String,
        @SerializedName(ApiDataKey.FIRST) val first: OddDetailData,
        @SerializedName(ApiDataKey.LATEST) val latest: OddDetailData,
        @SerializedName(ApiDataKey.LIVE) val live: OddDetailData,
        @SerializedName(ApiDataKey.CLOSED) val closed: Int
    )

    data class OddDetailData(
        @SerializedName(ApiDataKey.HOME) val home: String,
        @SerializedName(ApiDataKey.HANDICAP) val handicap: String,
        @SerializedName(ApiDataKey.AWAY) val away: String
    )

}