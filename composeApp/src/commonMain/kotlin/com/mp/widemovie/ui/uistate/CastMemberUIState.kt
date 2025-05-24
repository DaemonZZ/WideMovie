package com.mp.widemovie.ui.uistate

import androidx.compose.runtime.Stable

@Stable
data class CastMemberUIState(
    val name: String,
    val role: String,
    val imageUrl: String? = null, // or you can use painter/resource ID if from local
)
