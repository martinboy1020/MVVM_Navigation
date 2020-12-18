package com.example.mvvm_navigation.datacenter.network.response

import com.google.gson.annotations.SerializedName

class Search {

    data class SearchData(
        @SerializedName("leagues") val leagues: MutableList<LeaguesData> = mutableListOf(),
        @SerializedName("teams") val teams: MutableList<TeamsData> = mutableListOf(),
        @SerializedName("players") val players: MutableList<PlayersData> = mutableListOf()
    )

    data class LeaguesData(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("logo") val logo: String = "",
        @SerializedName("countryId") val countryId: Int = 0,
        @SerializedName("countryName") val countryName: String = "",
        @SerializedName("countryLogo") val countryLogo: String = ""
    )

    data class TeamsData(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("logo") val logo: String = "",
        @SerializedName("leagueId") val leagueId: Int = 0,
        @SerializedName("leagueName") val leagueName: String = "",
        @SerializedName("leagueLogo") val leagueLogo: String = ""
    )

    data class PlayersData(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("logo") val logo: String = "",
        @SerializedName("teamId") val teamId: Int = 0,
        @SerializedName("teamName") val teamName: String = "",
        @SerializedName("teamLogo") val teamLogo: String = ""
    )

}