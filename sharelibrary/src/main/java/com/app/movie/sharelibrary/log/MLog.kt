package com.app.movie.sharelibrary.log

import android.util.Log
import com.app.movie.sharelibrary.BuildConfig

class MLog {
    companion object {
        private const val TAG = BuildConfig.TAG
        fun d(tag: String, message: String) {
            if (BuildConfig.BUILD_TYPE == "debug") {
                val maxLogSize = 3000
                for (i in 0..message.length / maxLogSize) {
                    val start = i * maxLogSize
                    var end = (i + 1) * maxLogSize
                    end = if (end > message.length) message.length else end
                    Log.d("$TAG$tag", message.substring(start, end))
                }
            }
        }

        fun e(tag: String, message: String) {
            Log.e("$TAG$tag", message)
        }

        fun i(tag: String, message: String) {
            if (BuildConfig.BUILD_TYPE == "debug") {
                Log.i("$TAG$tag", message)
            }
        }

        fun w(tag: String, message: String) {
            if (BuildConfig.BUILD_TYPE == "debug") {
                Log.w("$TAG$tag", message)
            }
        }

        fun v(tag: String, message: String) {
            Log.v("$TAG$tag", message)
        }
    }
}