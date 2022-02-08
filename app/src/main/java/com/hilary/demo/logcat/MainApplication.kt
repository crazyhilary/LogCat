package com.hilary.demo.logcat

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.hilary.lib.logcat.utilities.ContextHelper

class MainApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
//        val myConfig = Configuration.Builder()
//            .setMinimumLoggingLevel(Log.VERBOSE)
//            .build()
//        WorkManager.initialize(baseContext, myConfig)
        ContextHelper.initContextHelper(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.ERROR)
            .build()
    }
}