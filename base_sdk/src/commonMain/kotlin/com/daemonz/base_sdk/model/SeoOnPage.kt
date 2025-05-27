package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeoOnPage(
    @SerialName("seoSchema") val seoSchema: SeoSchema? = null,
    val descriptionHead: String?,
    @SerialName("og_image")
    val ogImage: List<String>?,
    @SerialName("og_type")
    val ogType: String?,
    val titleHead: String?
): NetworkEntity()
