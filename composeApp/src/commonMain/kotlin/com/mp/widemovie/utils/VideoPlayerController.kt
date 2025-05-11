package com.mp.widemovie.utils

interface VideoPlayerController {
    fun play(url: String)
    fun pause()
    fun stop()
}