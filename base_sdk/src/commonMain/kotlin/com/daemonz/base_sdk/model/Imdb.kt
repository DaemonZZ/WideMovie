package com.daemonz.base_sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Imdb(
    @SerialName("id") val id: String?
)