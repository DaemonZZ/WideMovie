package com.mp.widemovie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform