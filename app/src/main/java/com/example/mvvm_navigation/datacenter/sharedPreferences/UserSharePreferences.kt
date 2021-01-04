package com.example.mvvm_navigation.datacenter.sharedPreferences

import android.content.Context

class UserSharePreferences constructor(context: Context) : SharedPreferencesHelper() {

    private var mContext: Context = context

    companion object {
        const val PREFERENCE_TABLE_NAME_USER = "User"
        const val KEY_USER_TOKEN = "user_token"

        const val PREFERENCE_TABLE_NAME_MATCH = "Match"
        const val KEY_MATCH_LIST_DATE = "match_list_date"
    }

    var userToken: String
        get() = getString(mContext, PREFERENCE_TABLE_NAME_USER, KEY_USER_TOKEN)
        set(value) = put(mContext, PREFERENCE_TABLE_NAME_USER, KEY_USER_TOKEN, value)

    var matchListDate: Long
        get() = getLong(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_DATE)
        set(value) = put(mContext, PREFERENCE_TABLE_NAME_MATCH, KEY_MATCH_LIST_DATE, value)

    fun removeData(preferencesName: String, key: String) {
        remove(mContext, preferencesName, key)
    }

}