package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Tmdb(
    val id: String?,
    val season: Int?,
    val type: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)