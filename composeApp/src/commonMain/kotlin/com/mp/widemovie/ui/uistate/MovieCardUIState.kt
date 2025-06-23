package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class MovieCardUIState(
    val title: String = "",
    val status: String = "",
    val posterUrl: String = "",
    val time: String = "",
    val slug: String,
)
