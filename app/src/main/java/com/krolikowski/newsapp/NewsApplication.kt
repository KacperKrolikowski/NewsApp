package com.krolikowski.newsapp

import android.app.Application
import android.os.StrictMode
import com.krolikowski.newsapp.networking.ConnectivityHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() {

    @Inject
    lateinit var connectivityHelper: ConnectivityHelper

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            enableStrictMode()
            Timber.plant(Timber.DebugTree())
        }
        connectivityHelper.init()
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }
}