package com.mp.widemovie.utils

import kotlinx.coroutines.CoroutineScope

expect fun provideIoCoroutineScope(): CoroutineScope

const val DEBUG = "debug"
const val RELEASE = "release"
const val VISIBLE = "com.mp.widemovie.utils.visible"
const val INVISIBLE = "com.mp.widemovie.utils.invisible"