package com.mp.widemovie

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import kotlinx.browser.window
import org.koin.core.scope.Scope

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(scope: Scope): Platform = WasmPlatform()

actual val CurrentUIType: UIType = UIType.Web


@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    return try {
        val screenWith = window.screen.width
        val screenHeight = window.screen.height
        Pair(screenWith, screenHeight)
    }catch (e: Exception){
        e.printStackTrace()
        Pair(1920, 1080)
    }
}
