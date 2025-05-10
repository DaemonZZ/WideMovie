package com.mp.widemovie.extensions

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

expect fun getInitialScreenFromUrl():Screen
@Composable
expect fun SyncNavigatorWithBrowser(navigator: Navigator)
expect fun buildQueryFromParams(params: Map<String, String>): String