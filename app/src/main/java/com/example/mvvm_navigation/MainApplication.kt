package com.example.mvvm_navigation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDex
import java.lang.ref.SoftReference

class MainApplication : Application() {

    private var mContextWk: SoftReference<Context>? = null
    private var mActivityWk: SoftReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()

        if (mContextWk == null) mContextWk = SoftReference(this@MainApplication)

        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
                mContextWk?.clear()
                mContextWk = SoftReference(activity.applicationContext)

                if (mActivityWk == null) mActivityWk = SoftReference(activity)
                mActivityWk?.clear()
                mActivityWk = SoftReference(activity)
            }

            override fun onActivityDestroyed(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {

            }

            override fun onActivityResumed(p0: Activity) {

            }

        })

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getCurrentActivity(): Activity? {
        return mActivityWk?.get()
    }

}