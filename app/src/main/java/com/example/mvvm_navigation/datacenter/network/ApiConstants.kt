package com.example.mvvm_navigation.datacenter.network

class ApiConstants {
    class HttpPath{
        companion object{
            const val Users = "users"
            const val UserFindIndex = "users/{index}"
            const val LOGIN = "v1/login"
            const val AUTH_REFRESH = "v1/auth/refresh"
            const val WEB_HOME_INFO = "v2/web/home/info"
            const val WEB_MATCHES_LIST = "v2/web/matches/list"
            const val TG_MATCHES_RECENT = "v2/tg/matches/recent"
            const val TG_MATCHES_BET_INFO = "v2/tg/matches/{matchId}/bet_info"
            const val STATISTICS_OCCURRENCE_RATE = "v2/statistics/occurrence_rate"
            const val SEARCH = "v2/search"
            const val MATCH_STATS = "v2/matches/{matchId}/stats"
            const val MATCH_DETAIL = "v2/matches/{matchId}/detail"
            const val MEMBERS_INFO = "/v2/members/info"
        }
    }

    class LoginApiHeader {

        companion object {
            const val USERNAME = "username"
            const val PASSWORD = "password"
            const val DEVICE = "device"
            const val TYPE = "type"
            const val TOKEN = "token"
            const val CAPTCHA = "captcha"
            const val IDENTITY = "identity"
        }

    }
}