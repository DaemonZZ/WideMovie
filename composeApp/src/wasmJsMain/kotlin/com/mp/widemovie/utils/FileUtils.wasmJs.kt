package com.mp.widemovie.utils

import com.daemonz.base_sdk.utils.TLog
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.url.URL
import org.w3c.fetch.Response
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

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
    } catch (e: Exception) {
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
    val data = JsArray<JsAny?>()
    lines.forEachIndexed { index, line ->
        data[index] = (line + "\n").toJsString()
    }
    val blob = Blob(data, BlobPropertyBag("text/plain"))
    val objectUrl = URL.createObjectURL(blob)
    TLog.d(TAG, "writeCacheFile: $objectUrl")
    window.sessionStorage.setItem("file-cached.txt", objectUrl)
    cacheFileEvent.onCachedFileCreated(objectUrl, slug)
}

actual fun deleteCachedFile() {

}
//fof testing
fun downloadFile(filename: String) {
    val url = window.sessionStorage.getItem(filename) ?: return
    TLog.d(TAG, "downloadFile: $url")
    val anchor = document.createElement("a") as org.w3c.dom.HTMLAnchorElement
    anchor.href = url
    anchor.download = filename
    anchor.click()
}