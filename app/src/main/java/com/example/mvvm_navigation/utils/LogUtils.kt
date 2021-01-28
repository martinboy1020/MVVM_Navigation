package com.example.mvvm_navigation.utils

import android.util.Log

class LogUtils {

    companion object {

        //        private val printLog = BuildConfig.DEBUG
        private val printLog = true

        fun v(tag: String, content: String) {
            if (printLog)
                Log.v(tag, content)
        }

        fun d(tag: String, content: String) {
            if (printLog)
                Log.d(tag, content)
        }

        fun i(tag: String, content: String) {
            if (printLog)
                Log.i(tag, content)
        }

        fun w(tag: String, content: String) {
            if (printLog)
                Log.w(tag, content)
        }

        fun e(tag: String, content: String) {
            if (printLog)
                Log.e(tag, content)
        }

        fun wtf(tag: String, content: String) {
            if (printLog)
                Log.wtf(tag, content)
        }

    }

}