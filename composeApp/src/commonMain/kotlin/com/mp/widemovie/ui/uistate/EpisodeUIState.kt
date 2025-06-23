package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class EpisodeUIState(
    val serverName: String = "",
    val serverData: List<EpisodeDetailUIState> = listOf(),
    val selectedEpisode :EpisodeDetailUIState? = serverData.firstOrNull(),
)

@Stable
data class EpisodeDetailUIState(
    val slug: String,
    val title: String,
    val thumbnailUrl: String? = null,
    val m3u8Url: String? = null,
    val url: String? = null,
)
