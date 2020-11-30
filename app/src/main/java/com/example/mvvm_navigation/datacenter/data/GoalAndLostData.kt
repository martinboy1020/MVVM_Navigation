package com.example.mvvm_navigation.datacenter.data

import android.os.Parcel
import android.os.Parcelable
import com.example.mvvm_navigation.widget.GoalAndLostDataWidget

data class GoalAndLostData(
    val type: GoalAndLostDataWidget.Type,
    val avgGoal: Float = 0f,
    val avgLost: Float = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        GoalAndLostDataWidget.Type.valueOf(parcel.readString().toString()),
        parcel.readFloat(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type.name)
        parcel.writeFloat(avgGoal)
        parcel.writeFloat(avgLost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalAndLostData> {
        override fun createFromParcel(parcel: Parcel): GoalAndLostData {
            return GoalAndLostData(parcel)
        }

        override fun newArray(size: Int): Array<GoalAndLostData?> {
            return arrayOfNulls(size)
        }
    }
}