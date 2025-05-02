package com.mp.widemovie

import org.koin.core.scope.Scope

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(scope: Scope): Platform = WasmPlatform()