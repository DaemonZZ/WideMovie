package com.daemonz.base_sdk.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeoOnPage(
    val descriptionHead: String?,
    @SerialName("og_image")
    val ogImage: List<String>?,
    @SerialName("og_type")
    val ogType: String?,
    val titleHead: String?
)