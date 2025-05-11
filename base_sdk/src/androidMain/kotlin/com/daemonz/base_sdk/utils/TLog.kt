package com.daemonz.base_sdk.utils

import android.util.Log
import com.daemonz.base_sdk.TAG

actual object TLog {
    actual fun d(tag: String?, message: String) {
        Log.d("$TAG$tag", message)
    }

    actual fun e(tag: String?, message: String, throwable: Throwable?) {
        Log.e("$TAG$tag", message, throwable)
    }
}