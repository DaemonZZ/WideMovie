package com.app.movie.sharelibrary.utils

interface CacheFileEvent {
    fun onCachedFileCreated(file: String, slug: String)
    fun onCachedError(e: String)
    //for test
    fun tsCollected(listTs: List<String>, listAbnormal: Map<Int, Int>) {}
}