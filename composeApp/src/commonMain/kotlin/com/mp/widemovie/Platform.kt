package com.mp.widemovie

import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform