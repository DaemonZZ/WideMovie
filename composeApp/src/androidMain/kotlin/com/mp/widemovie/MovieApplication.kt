package com.mp.widemovie

import android.app.Application
import android.content.Context
import com.mp.widemovie.di.initKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initKoin()
    }
    companion object {
        lateinit var appContext: Context
            private set
    }
}