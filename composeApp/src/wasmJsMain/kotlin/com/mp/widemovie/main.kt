package com.mp.widemovie

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import cafe.adriel.voyager.navigator.Navigator
import com.mp.widemovie.di.initKoin
import com.mp.widemovie.screen.HomeScreen
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    ComposeViewport(document.body!!) {
        Navigator(HomeScreen())
    }
}