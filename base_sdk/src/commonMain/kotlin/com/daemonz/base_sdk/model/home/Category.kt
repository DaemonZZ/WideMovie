package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String?,
    val name: String?,
    val slug: String?
)