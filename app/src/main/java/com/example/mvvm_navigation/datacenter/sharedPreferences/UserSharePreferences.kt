package com.example.mvvm_navigation.datacenter.sharedPreferences

import android.content.Context
import com.example.mvvm_navigation.utils.LogUtils
import com.google.gson.Gson

class UserSharePreferences constructor(context: Context) : SharedPreferencesHelper() {

    private var mContext: Context = context

    companion object {
        const val PREFERENCE_TABLE_NAME_USER = "User"
        const val KEY_USER_TOKEN = "user_token"

        const val PREFERENCE_TABLE_NAME_MATCH = "Match"
        const val KEY_MATCH_LIST_DATE = "match_list_date"
        const val KEY_MATCH_LIST_FILTER_LEAGUE = "match_list_filter_league"
    }

    var userToken: String
        get() = getString(mContext, PREFERENCE_TABLE_NAME_USER, KEY_USER_TOKEN)
        set(value) = put(mContext, PREFERENCE_TABLE_NAME_USER, KEY_USER_TOKEN, value)

    var matchListDate: Long
        get() = getLong(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_DATE)
        set(value) = put(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_DATE, value)

    fun getMatchListFilterLeague(): MutableList<Int>? {
        val json =
            getString(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_FILTER_LEAGUE)
        LogUtils.d("tag11111111", "getMatchListFilterLeague json: $json")
        return if(json.isNotEmpty()) {
            Gson().fromJson(
                json, Array<Int>::class.java
            ).toList() as MutableList<Int>
        } else {
            null
        }
    }

    fun setMatchListFilterLeague(value: MutableList<Int>?) {
        if(value.isNullOrEmpty()) {
            removeData(PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_FILTER_LEAGUE)
        } else {
            put(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_FILTER_LEAGUE, Gson().toJson(value))
        }
    }

    fun removeData(preferencesName: String, key: String) {
        remove(mContext, preferencesName, key)
    }

}