package com.example.mvvm_navigation.datacenter.sharedPreferences

import android.content.Context

class UserSharePreferences constructor(context: Context) : SharedPreferencesHelper() {

    private var mContext: Context = context

    companion object {
        const val PREFERENCE_TABLE_NAME = "User"
        const val KEY_USER_TOKEN = "user_token"
    }

    var userToken: String
        get() = getString(mContext, PREFERENCE_TABLE_NAME, KEY_USER_TOKEN)
        set(value) = put(mContext, PREFERENCE_TABLE_NAME, KEY_USER_TOKEN, value)

}