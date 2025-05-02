package com.mp.widemovie

import android.app.Application
import com.mp.widemovie.di.initKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}