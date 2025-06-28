package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class ContentUIState(
    val title: String = "",
    val rating: Float = 0.0f,
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
    val time: String = "",
    val lang: String = "",
    val status: String = "",
    val quality: String = "",
    val trailerUrl: String? = null,
)

