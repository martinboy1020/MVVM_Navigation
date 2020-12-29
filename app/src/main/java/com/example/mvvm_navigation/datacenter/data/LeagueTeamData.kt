package com.example.mvvm_navigation.datacenter.data

import android.os.Parcel
import android.os.Parcelable

data class LeagueTeamData(
    val leagueId: Int = 0,
    val leagueName: String = "",
    val leagueLogo: String = "",
    val homeId: Int = 0,
    val homeName: String,
    val homeLogo: String = "",
    val awayId: Int = 0,
    val awayName: String = "",
    val awayLogo: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(leagueId)
        parcel.writeString(leagueName)
        parcel.writeString(leagueLogo)
        parcel.writeInt(homeId)
        parcel.writeString(homeName)
        parcel.writeString(homeLogo)
        parcel.writeInt(awayId)
        parcel.writeString(awayName)
        parcel.writeString(awayLogo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeagueTeamData> {
        override fun createFromParcel(parcel: Parcel): LeagueTeamData {
            return LeagueTeamData(parcel)
        }

        override fun newArray(size: Int): Array<LeagueTeamData?> {
            return arrayOfNulls(size)
        }
    }
}