package com.mp.widemovie.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

data class MovieDetail(val slug: String) : Screen {
    @Composable
    override fun Content() {
        Text("DetailScreen: $slug")
    }
}