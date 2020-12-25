package com.example.mvvm_navigation.datacenter.data

import android.os.Parcel
import android.os.Parcelable

data class LeagueTeamData(
    val leagueId: Int = 0,
    val leagueName: String = "",
    val homeId: Int = 0,
    val homeName: String,
    val awayId: Int = 0,
    val awayName: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(leagueId)
        parcel.writeString(leagueName)
        parcel.writeInt(homeId)
        parcel.writeString(homeName)
        parcel.writeInt(awayId)
        parcel.writeString(awayName)
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