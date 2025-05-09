package com.mp.widemovie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.mp.widemovie.extensions.SyncNavigatorWithBrowser
import com.mp.widemovie.extensions.getInitialScreenFromUrl
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val TAG = "App"
@Composable
@Preview
fun App() {
    val initialScreen = remember { getInitialScreenFromUrl() }

    Navigator(screen = initialScreen) { navigator ->
        SyncNavigatorWithBrowser(navigator)
        CurrentScreen()
    }
}