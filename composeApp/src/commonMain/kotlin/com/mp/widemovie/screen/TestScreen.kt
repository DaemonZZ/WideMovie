package com.mp.widemovie.screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams

class TestScreen(val param: String?): BaseScreen() {
    override val key: ScreenKey
        get() = "${ super.key }?${buildQueryFromParams(mapOf("param" to param.toString()))}"
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        Button(
            onClick = {
                nav.pop()
            }
        ) {
            Text("Test")
        }
    }
}