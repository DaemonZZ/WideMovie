package com.mp.widemovie

import androidx.compose.runtime.Composable
import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

expect val CurrentUIType: UIType

@Composable
expect fun getScreenSize(): Pair<Int, Int>

enum class UIType {
    Android,
    Web,
    Ios
}