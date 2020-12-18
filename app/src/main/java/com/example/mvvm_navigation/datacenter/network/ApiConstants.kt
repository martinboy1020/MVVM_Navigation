package com.example.mvvm_navigation.datacenter.network

class ApiConstants {
    class HttpPath{
        companion object{
            const val Users = "users"
            const val UserFindIndex = "users/{index}"
            const val LOGIN = "v1/login"
            const val AUTH_REFRESH = "v1/auth/refresh"
            const val WEB_HOME_INFO = "v2/web/home/info"
            const val WEB_HOME_MATCHES_RECENT = "v2/web/home/matches/recent"
            const val WEB_HOME_MATCHES_STATISTICS = "v2/web/home/matches/statistics"
            const val WEB_HOME_MATCHES_BET_INFO = "v2/web/home/matches/{matchId}/bet_info"
            const val WEB_LIVE_MATCHES_LIST = "v2/web/live/matches/list"
            const val SEARCH = "v2/search"
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