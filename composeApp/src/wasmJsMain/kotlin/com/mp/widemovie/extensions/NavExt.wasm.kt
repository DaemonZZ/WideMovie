package com.mp.widemovie.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.mp.widemovie.screen.HomeScreen
import com.mp.widemovie.screen.MovieDetail
import kotlinx.browser.window

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

actual fun getInitialScreenFromUrl(): Screen {
    val path = window.location.pathname.removePrefix("/")

    return when {
        path.isBlank() || path == "home" -> HomeScreen()

        path.startsWith("detail/") -> {
            val id = path.removePrefix("detail/")
            MovieDetail(id)
        }

        else -> HomeScreen() // fallback nếu route không hợp lệ
    }
}
