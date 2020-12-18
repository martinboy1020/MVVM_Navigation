package com.example.mvvm_navigation.datacenter.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

open class SharedPreferencesHelper {

    val DEFUALT_STRING = ""
    val DEFAULT_BOOLEAN = false
    val DEFAULT_INT = 0
    val DEFAULT_FLOAT = 0f
    val DEFAULT_LONG = 0L

    protected fun put(context: Context, preferencesName: String, key: String, value: Any?){
        val editor = getEditor(context, preferencesName)
        when(value){
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }
        editor.apply()
    }
    protected fun get(context: Context, preferencesName: String, key: String, defaultValue: Any?): Any?{
        val preference = getSharePreferences(context, preferencesName)
        return when(defaultValue){
            is String -> preference.getString(key, defaultValue)
            is Int -> preference.getInt(key, defaultValue)
            is Boolean -> preference.getBoolean(key, defaultValue)
            is Float -> preference.getFloat(key, defaultValue)
            is Long -> preference.getLong(key, defaultValue)
            else -> null
        }
    }
    protected fun remove(context: Context, preferencesName: String, key: String){
        val editor = getEditor(context, preferencesName)
        editor.remove(key)
        editor.apply()
    }
    fun getString(context: Context, preferencesName: String, key: String, defaultValue: String = this.DEFUALT_STRING): String =
        getSharePreferences(context, preferencesName).getString(key, defaultValue).toString()
    fun getInt(context: Context, preferencesName: String, key: String, defaultValue: Int = this.DEFAULT_INT): Int = getSharePreferences(context, preferencesName).getInt(key, defaultValue)
    fun getBoolean(context: Context, preferencesName: String, key: String, defaultValue: Boolean = this.DEFAULT_BOOLEAN): Boolean = getSharePreferences(context, preferencesName).getBoolean(key, defaultValue)
    fun getFloat(context: Context, preferencesName: String, key: String, defaultValue: Float = this.DEFAULT_FLOAT): Float = getSharePreferences(context, preferencesName).getFloat(key, defaultValue)
    fun getLong(context: Context, preferencesName: String, key: String, defaultValue: Long = this.DEFAULT_LONG): Long = getSharePreferences(context, preferencesName).getLong(key, defaultValue)
    private fun getSharePreferences(context: Context, preferencesName: String): SharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    private fun getEditor(context: Context, preferencesName: String) = getSharePreferences(context, preferencesName).edit()
}