package com.mp.widemovie.screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class MovieDetail(val slug: String) : Screen {
    override val key = "detail/$slug"
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        Button(
            onClick = {
                nav.pop()
            }
        ) {
            Text("Back")
        }
    }
}