package com.app.movie.sharelibrary.utils

import android.annotation.SuppressLint
import com.app.movie.sharelibrary.log.MLog
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class VideoFileMaker(private val cacheFileEvent: CacheFileEvent) {
    companion object {
        private const val TAG = "VideoFileMaker"

        @SuppressLint("SdCardPath")
        const val CACHE_DIR = "/sdcard/Documents/"
        val EXPECTED_ABNORMAL = listOf(149, 450, 600)
    }


    private var m3u8Url: String = ""
    private var referer: String = ""
    private var mSlug = ""

    fun setM3U8Url(url: String) {
        MLog.d(TAG, "setM3U8Url: $url")
        if (url.contains("index.m3u8")) {
            m3u8Url = url.replace("index.m3u8", "3000k/hls/mixed.m3u8")
            referer = url.replace("index.m3u8", "3000k/hls/")
            MLog.d(TAG, "setM3U8Url after: $m3u8Url")
            MLog.d(TAG, "referer after: $referer")
        } else {
            MLog.e(TAG, "setM3U8Url: url is invalid")
            m3u8Url = ""
            referer = ""
        }
    }

    fun setSlug(slug: String) {
        mSlug = slug
    }

    private fun fetchM3U8Content(): List<String> {
        MLog.d(TAG, "fetchM3U8Content")
        val lines = mutableListOf<String>()
        val url = URL(m3u8Url)
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
            MLog.e(TAG, "fetchM3U8Content: ${e.message}")
            e.printStackTrace()
            cacheFileEvent.onCachedError(e.message.toString())
        } finally {
            connection.disconnect()
        }
        return lines
    }

    private fun writeToFile(filePath: String, lines: List<String>) {
        val folder = File(CACHE_DIR)
        if (!folder.exists()) {
            val x = folder.mkdir()
            MLog.d(TAG, "mkdir: $x")
        }
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
        file.printWriter().use { out ->
            lines.forEach { line ->
                out.println(line)
            }
        }

        cacheFileEvent.onCachedFileCreated(filePath, mSlug)
    }

    fun convertFile() {
        MLog.d(TAG, "convertFile: $m3u8Url")

        try {
            val contentLines = fetchM3U8Content()
            var count = 0
            var skip = 0
            val result = mutableListOf<String>()
            for (i in contentLines.indices) {
                if (contentLines[i].contains(".ts") && skip == 0) {
                    count++
                } else if (contentLines[i].contains("DISCONTINUITY") && contentLines[i + 1].contains(
                        "EXTINF:2.000000"
                    ) && skip == 0
                ) {
                    if(count <= 600 && EXPECTED_ABNORMAL.contains(count) ||  count > 600){
                        MLog.d(TAG, "====================== DETECT ABNORMAL =====================")
                        MLog.d(TAG, "count: $count")
                        skip = 2
                        count = 0
                    }
                } else if (skip > 0 && contentLines[i].contains("DISCONTINUITY")) {
                    skip--
                }
                if (skip == 0) {
                    if (contentLines[i].contains(".ts")) {
                        val fullTsLink = referer + contentLines[i]
                        MLog.d(TAG, "fullTsLink: $fullTsLink")
                        result.add(fullTsLink)
                    } else {
                        result.add(contentLines[i])
                    }
                }
            }
            writeToFile("${CACHE_DIR}mixed_converted.m3u8", result)

        } catch (e: Exception) {
            MLog.e(TAG, "convertFile: ${e.message}")
            cacheFileEvent.onCachedError("convertFile: ${e.message}")
            e.printStackTrace()
        }
    }

    fun deleteCacheFile() {
        MLog.d(TAG, "deleteCacheFile")
        try {
            val file = File("${CACHE_DIR}mixed_converted.m3u8")
            if (file.exists()) {
                file.delete()
            }
            val testFile = File("${CACHE_DIR}ts_collected.txt")
            if (testFile.exists()) {
                testFile.delete()
            }
        } catch (e: Exception) {
            MLog.e(TAG, "deleteCacheFile: ${e.message}")
            e.printStackTrace()
        }
    }

    //For Integration Test
    fun collectTs() {
        try {
            val contentLines = fetchM3U8Content()
            var count = 0
            var realIndex = 0
            var skip = 0
            val result = mutableListOf<String>()
            val listAbnormal = mutableMapOf<Int, Int>()
            for (i in contentLines.indices) {
                if (contentLines[i].contains(".ts") && skip == 0) {
                    count++
                    realIndex++
                } else if (contentLines[i].contains("DISCONTINUITY") && contentLines[i + 1].contains(
                        "EXTINF:2.000000"
                    ) && skip == 0
                ) {
                    if(count <= 600 && EXPECTED_ABNORMAL.contains(count) ||  count > 600){
                        println( "====================== DETECT ABNORMAL =====================")
                        println( "count: $count")
                        skip = 2
                        listAbnormal.put(count, realIndex)
                        count = 0
                    }
                } else if (skip > 0 && contentLines[i].contains("DISCONTINUITY")) {
                    skip--
                }

                if (contentLines[i].contains(".ts")) {
                    val fullTsLink = referer + contentLines[i]
                    result.add(fullTsLink)
                }

            }
            cacheFileEvent.tsCollected(result, listAbnormal)

        } catch (e: Exception) {
            cacheFileEvent.onCachedError("collectTs: ${e.message}")
            e.printStackTrace()
        }
    }

}