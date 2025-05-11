package com.mp.widemovie.utils

expect fun readM3u8File(path: String, referer:String,cacheFileEvent: CacheFileEvent): List<String>
expect fun writeCacheFile(path: String, lines: List<String>, slug: String, cacheFileEvent: CacheFileEvent)
expect fun deleteCachedFile()