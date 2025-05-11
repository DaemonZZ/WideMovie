package com.mp.widemovie.utils

import android.os.Environment
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.MovieApplication
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "FileUtils.android"

actual fun readM3u8File(path: String, referer:String, cacheFileEvent: CacheFileEvent): List<String>  {
    val lines = mutableListOf<String>()
    val url = URL(path)
    val connection = url.openConnection() as HttpURLConnection
    connection.setRequestProperty("Referer", referer)
    connection.connectTimeout = 5000
    connection.readTimeout = 5000
    try {
        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            line?.let { lines.add(it.trim()) }
        }
        reader.close()
    } catch (e: Exception) {
        TLog.e(TAG, "fetchM3U8Content: ${e.message}")
        e.printStackTrace()
        cacheFileEvent.onCachedError(e.message.toString())
    } finally {
        connection.disconnect()
    }
    return lines
}

actual fun writeCacheFile(path: String, lines: List<String>, slug: String, cacheFileEvent: CacheFileEvent) {
    val folder = MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    if (folder?.exists() == false) {
        val x = folder.mkdir()
        TLog.d(TAG, "mkdir: $x")
    }
    val file = File(MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),path)
    if (file.exists()) {
        file.delete()
    }
    file.printWriter().use { out ->
        lines.forEach { line ->
            out.println(line)
        }
    }

    cacheFileEvent.onCachedFileCreated(path, slug)
}

actual fun deleteCachedFile() {
    try {
        val file =File(MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "mixed_converted.m3u8")
        if (file.exists()) {
            file.delete()
        }
        val testFile = File(MovieApplication.appContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ts_collected.txt")
        if (testFile.exists()) {
            testFile.delete()
        }
    } catch (e: Exception) {
        TLog.e(TAG, "deleteCacheFile: ${e.message}")
        e.printStackTrace()
    }
}