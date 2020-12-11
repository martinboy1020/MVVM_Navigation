package com.example.mvvm_navigation.datacenter.data

data class MatchListItem(
    var matchId: Int = 0,
    var status: Int = 0,
    var openDate: Int = 0,
    var startTime: Int = 0,
    var homeName: String = "",
    var awayName: String = "",
    var homeLogo: String = "",
    var awayLogo: String = "",
    var homeScore: Int = 0,
    var awayScore: Int = 0,
    var homeHalfScore: Int = 0,
    var awayHalfScore: Int = 0,
    var homeRedCard: Int = 0,
    var awayRedCard: Int = 0,
    var homeYellowCard: Int = 0,
    var awayYellowCard: Int = 0,
    var homeCorner: Int = 0,
    var awayCorner: Int = 0,
    var animation: Int = 0,
    var liveStreaming: Int = 0,
    var fullGameTime: String = "",
    var isTopOfList: Boolean = false
)