package com.daemonz.base_sdk.model

import kotlinx.serialization.SerialName
data class SeoOnPage(
    @SerialName("seoSchema") val seoSchema: SeoSchema? = null,
)
