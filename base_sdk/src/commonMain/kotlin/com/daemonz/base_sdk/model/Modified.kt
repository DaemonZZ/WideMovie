package com.daemonz.base_sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Modified(
    @SerialName("time")
    val time: String?
)