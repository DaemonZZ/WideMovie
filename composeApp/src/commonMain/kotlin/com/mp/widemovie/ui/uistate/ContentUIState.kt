package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class ContentUIState(
    val title: String = "",
    val rating: Double = 0.0,
    val releaseYear: Int = 0,
    val ageRating: String = "",
    val country: String = "",
    val hasSubtitle: Boolean = false,
    val isBookmarked: Boolean = false,
    val isShared: Boolean = false,
    val isPlayable: Boolean = true,
    val isDownloadable: Boolean = true,
    val genres: List<String> = emptyList(),
    val description: String = "",
    val posterUrl: String = "",
    val cast: List<CastMemberUIState> = emptyList(),
)

