package com.example.mvvm_navigation.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

object LangUtils {

    const val FOLLOW_SYSTEM = 0
    const val SIMPLE_CHINESE = 1
    const val TRADITION_CHINESE = 2
    const val ENGLISH = 3

    private fun getCurrentLang(userLang: String): Locale {
        return when (userLang) {
            Constants.LANGUAGE.SCHINESE -> Locale.SIMPLIFIED_CHINESE
            Constants.LANGUAGE.TCHINESE -> Locale.TRADITIONAL_CHINESE
            else -> Locale.SIMPLIFIED_CHINESE
        }
    }

    fun getAttachBaseContext(
        context: Context,
        lang: String
    ): Context {
        LogUtils.d(
            "tag1",
            "配置语言...默认locale=" + Locale.getDefault() + ";用户设置的为=" + getCurrentLang(
                lang
            )
        )

            //Android 7.0之後需要用另一種方​​式更改res語言,即配置進context中
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, lang)
        } else {
            //7.0之前的更新語言資源方式
            changeResLanguage(context, lang)
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(
        context: Context,
        lang: String
    ): Context {
        val resources = context.resources
        val locale = getCurrentLang(lang)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    @SuppressLint("NewApi")
    private fun changeResLanguage(
        context: Context,
        lang: String
    ) {
        val resources = context.resources
        val configuration = resources.configuration
        val locale = getCurrentLang(lang)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
