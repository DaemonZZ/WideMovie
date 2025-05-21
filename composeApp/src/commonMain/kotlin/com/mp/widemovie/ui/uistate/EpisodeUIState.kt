package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class EpisodeUIState(
    val serverName: String = "",
    val serverData: List<EpisodeDetailUIState> = listOf(),
)

@Stable
data class EpisodeDetailUIState(
    val slug: String,
    val title: String,
    val thumbnailUrl: String? = null,
)
