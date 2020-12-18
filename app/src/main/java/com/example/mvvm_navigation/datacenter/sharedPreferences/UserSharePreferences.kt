package com.example.mvvm_navigation.datacenter.sharedPreferences

import android.content.Context

class UserSharePreferences(context: Context) : SharedPreferencesHelper() {

    private var mContext: Context = context

    companion object {
        const val PREFERENCE_TABLE_NAME = "User"
        const val KEY_USER_TOKEN = "user_token"
    }

    var getUserToken: String
        get() = getString(mContext, PREFERENCE_TABLE_NAME, "")
        set(value) = put(mContext, PREFERENCE_TABLE_NAME, KEY_USER_TOKEN, value)

}