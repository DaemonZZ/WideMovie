package com.mp.widemovie

import android.content.Context
import android.os.Build
import org.koin.core.scope.Scope

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())

actual val CurrentUIType: UIType = UIType.Android