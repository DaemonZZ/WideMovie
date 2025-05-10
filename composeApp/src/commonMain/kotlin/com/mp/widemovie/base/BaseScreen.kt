package com.mp.widemovie.base

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import kotlin.reflect.KClass

abstract class BaseScreen: Screen {
    protected val TAG = this::class.simpleName
    override val key: ScreenKey
        get() = getScreenKey(this::class)
}

 fun getScreenKey(clazz: KClass<out BaseScreen>): String {
     val className = clazz.simpleName ?: ""
    return if(className.contains("Screen")){
        className.replace("Screen", "")
    }else if(className.contains("screen")) {
        className.replace("screen", "")
    }else{
        className
    }
}
