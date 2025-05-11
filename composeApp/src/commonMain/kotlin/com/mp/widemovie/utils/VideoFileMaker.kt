package com.mp.widemovie.utils

import com.daemonz.base_sdk.utils.TLog
import kotlinx.coroutines.CoroutineScope


class VideoFileMaker(
    private val cacheFileEvent: CacheFileEvent,
    private val ioScope: CoroutineScope
) {
    companion object {
        private const val TAG = "VideoFileMaker"

        val EXPECTED_ABNORMAL = listOf(149, 450, 600)
    }


    private var m3u8Url: String = ""
    private var referer: String = ""
    private var mSlug = ""

    fun setM3U8Url(url: String) {
        TLog.d(TAG, "setM3U8Url: $url")
        if (url.contains("index.m3u8")) {
            m3u8Url = url.replace("index.m3u8", "3000k/hls/mixed.m3u8")
            referer = url.replace("index.m3u8", "3000k/hls/")
            TLog.d(TAG, "setM3U8Url after: $m3u8Url")
            TLog.d(TAG, "referer after: $referer")
        } else {
            TLog.e(TAG, "setM3U8Url: url is invalid")
            m3u8Url = ""
            referer = ""
        }
    }

    fun setSlug(slug: String) {
        mSlug = slug
    }

    private fun fetchM3U8Content(): List<String> {
        TLog.d(TAG, "fetchM3U8Content: ${ioScope.coroutineContext}")
        return readM3u8File(m3u8Url, referer, cacheFileEvent)
    }

    private fun writeToFile(filePath: String, lines: List<String>) {
        writeCacheFile(filePath, lines, mSlug, cacheFileEvent)
    }

    fun convertFile() {
        TLog.d(TAG, "convertFile: $m3u8Url")
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
                    if (count <= 600 && EXPECTED_ABNORMAL.contains(count) || count > 600) {
                        TLog.d(TAG, "====================== DETECT ABNORMAL =====================")
                        TLog.d(TAG, "count: $count")
                        skip = 2
                        count = 0
                    }
                } else if (skip > 0 && contentLines[i].contains("DISCONTINUITY")) {
                    skip--
                }
                if (skip == 0) {
                    if (contentLines[i].contains(".ts")) {
                        val fullTsLink = referer + contentLines[i]
                        TLog.d(TAG, "fullTsLink: $fullTsLink")
                        result.add(fullTsLink)
                    } else {
                        result.add(contentLines[i])
                    }
                }
            }
            writeToFile("mixed_converted.m3u8", result)

        } catch (e: Exception) {
            TLog.e(TAG, "convertFile: ${e.message}")
            cacheFileEvent.onCachedError("convertFile: ${e.message}")
            e.printStackTrace()
        }
    }

    fun deleteCacheFile() {
        TLog.d(TAG, "deleteCacheFile")
        deleteCachedFile()
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
                    if (count <= 600 && EXPECTED_ABNORMAL.contains(count) || count > 600) {
                        println("====================== DETECT ABNORMAL =====================")
                        println("count: $count")
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