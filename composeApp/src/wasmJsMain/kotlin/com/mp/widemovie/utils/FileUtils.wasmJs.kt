package com.mp.widemovie.utils

import com.daemonz.base_sdk.utils.TLog
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response

private const val TAG = "FileUtils.wasmJs"
actual suspend fun readM3u8File(
    path: String,
    referer: String,
    cacheFileEvent: CacheFileEvent
): List<String> {
    try {
        val res: Response = window.fetch(path).await()
        val lines = res.text().await<CharSequence>().toString().split("\n")
        return lines
    } catch (e: Exception){
        TLog.e(TAG, "readM3u8File: ${e.message}")
        e.printStackTrace()
        cacheFileEvent.onCachedError(e.message.toString())
        return emptyList()
    }

}

actual fun writeCacheFile(
    path: String,
    lines: List<String>,
    slug: String,
    cacheFileEvent: CacheFileEvent
) {

}

actual fun deleteCachedFile() {

}