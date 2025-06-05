package com.mp.widemovie

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import org.koin.core.scope.Scope

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())

actual val CurrentUIType: UIType = UIType.Android

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return Pair(screenWith, screenHeight)
}