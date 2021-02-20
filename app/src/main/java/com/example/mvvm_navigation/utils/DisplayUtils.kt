package com.example.mvvm_navigation.utils

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity

object DisplayUtils {

    /**
     * Covert dp to px
     * @param dp
     * @param context
     * @return pixel
     */
    fun convertDpToPixel(context: Context, dp: Float): Float {
        return dp * getDensity(context)
    }

    /**
     * Covert px to dp
     * @param px
     * @param context
     * @return dp
     */
    fun convertPixelToDp(context: Context, px: Float): Float {
        return px / getDensity(context)
    }

    /**
     * Covert px to sp
     *
     * @param pxValue
     * @param fontScale
     * （DisplayMetrics類中屬性scaledDensity）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f)
    }

    /**
     * Covert sp to px
     *
     * @param spValue
     * @param fontScale
     * （DisplayMetrics類中屬性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }

    /**
     * 取得螢幕密度
     * 120dpi = 0.75
     * 160dpi = 1 (default)
     * 240dpi = 1.5
     * @param context
     * @return
     */
    fun getDensity(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return metrics.density
    }

    /**
     * 獲得狀態列高度
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 判斷手機螢幕橫豎狀態
     * @param newConfig
     * @param activity
     * @return
     */
    fun checkMobileRotationIsLandScape(newConfig: Configuration, activity: AppCompatActivity): Boolean {
        // 為了7.0無法從newConfig抓到正確的轉向值 所以透過getWindowManager().getDefaultDisplay().getRotation()抓目前螢幕的旋轉值
        val rotation = activity.windowManager.defaultDisplay.rotation
        val angle = if (rotation == Surface.ROTATION_90) 90 else if (rotation == Surface.ROTATION_180) 180 else if (rotation == Surface.ROTATION_270) 270 else 0
        return newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE || angle == 90 || angle == 270
    }

}