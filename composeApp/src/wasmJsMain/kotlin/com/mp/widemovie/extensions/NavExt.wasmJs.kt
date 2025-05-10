package com.mp.widemovie.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.daemonz.base_sdk.utils.TLog
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

private const val TAG = "NavExt.wasm"
@Composable
actual fun SyncNavigatorWithBrowser(navigator: Navigator) {
    val currentScreenKey = navigator.lastItemOrNull?.key

    LaunchedEffect(currentScreenKey) {
        val newPath = "/$currentScreenKey"
        val currentPath = window.location.pathname
        if (newPath != currentPath && currentScreenKey != null) {
            window.history.pushState(null, "", newPath)
        }
    }

    LaunchedEffect(Unit) {
        window.onpopstate = {
            navigator.pop()
        }
    }
}

/***
 * Xử lý url khi truy cập từ direct link
 */
actual fun getInitialScreenFromUrl(): Screen {
    val path = window.location.pathname.removePrefix("/")
    TLog.d(TAG, "getInitialScreenFromUrl: $path")
    return ScreenMapping.getScreen(path)
}

actual fun buildQueryFromParams(params: Map<String, String>): String {
    val searchParams = URLSearchParams()
    params.forEach { (k, v) -> searchParams.append(k, v) }
    return searchParams.toString()
}
