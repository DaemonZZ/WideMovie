package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName

data class ListData(
    @SerialName("data") val data: Data,
    @SerialName("status") val status: String = "",
    @SerialName("message") val message: String = ""
) : NetworkEntity()
