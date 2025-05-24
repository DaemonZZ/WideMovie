package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Params(
    val filterCategory: List<String>?,
    val filterCountry: List<String>?,
    val filterYear: String?,
    val itemsSportsVideosUpdateInDay: Int?,
    val itemsUpdateInDay: Int?,
    val pagination: Pagination?,
    val sortField: String?,
    val totalSportsVideos: Int?,
    @SerialName("type_slug")
    val typeSlug: String?
)