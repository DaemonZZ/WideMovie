package com.mp.widemovie.utils

import android.app.Activity
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

fun Activity.hideSystemUI() {
    //Hides the ugly action bar at the top if need
    actionBar?.hide()
    //Hide the status bars
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.insetsController?.apply {
        hide(WindowInsets.Type.statusBars())
        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Activity.showSystemUI() {
    //Show the ugly action bar at the top if need
    actionBar?.show()
    //Hide the status bars
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.insetsController?.apply {
        show(WindowInsets.Type.statusBars())
        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}