package com.app.movie.sharelibrary.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object AppUtils {
    fun Long.timeToString(): String {
        val sumSeconds = this
        val hours = sumSeconds / 3600
        val minute = sumSeconds % 3600 / 60
        val seconds = sumSeconds % 60
        val hString = if (hours < 10) "0$hours" else "$hours"
        val mString = if (minute < 10) "0$minute" else "$minute"
        val sString = if (seconds < 10) "0$seconds" else "$seconds"
        return if (hours == 0L) {
            "$mString:$sString"
        } else "$hString:$mString:$sString"
    }

    fun String.getBitmapFromURL(): Bitmap? {
        return try {
            val url = URL(this)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }

    fun <T> Response<T>.addOnCompleteListener(listener: (T) -> Unit): Response<T> {
        if (this.isSuccessful && this.body() != null) {
            this.body()?.let { listener.invoke(it) }
        }
        return this
    }

    fun <T> Response<T>.addOnFailureListener(listener: (String) -> Unit): Response<T> {
        if (!this.isSuccessful || this.errorBody() != null) {
            this.errorBody()?.let { listener.invoke(it.string()) }
        }
        return this
    }
}