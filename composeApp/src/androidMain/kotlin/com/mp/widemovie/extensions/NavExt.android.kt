package com.mp.widemovie.extensions

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.mp.widemovie.screen.HomeScreen

actual fun getInitialScreenFromUrl(): Screen = HomeScreen()

@Composable
actual fun SyncNavigatorWithBrowser(navigator: Navigator) {
}