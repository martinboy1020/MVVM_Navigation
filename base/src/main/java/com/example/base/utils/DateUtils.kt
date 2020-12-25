package com.example.base.utils

object DateUtils {

    fun getTodayDateSeconds(): Long {
        return System.currentTimeMillis() / 1000
    }

}