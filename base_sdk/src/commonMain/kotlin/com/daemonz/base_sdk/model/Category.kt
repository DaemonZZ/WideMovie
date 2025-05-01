package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Resource("/category")
@Serializable
data class Category(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String
) : NetworkEntity()
