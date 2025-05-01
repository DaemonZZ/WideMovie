package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName

data class Category(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String
) : NetworkEntity()
