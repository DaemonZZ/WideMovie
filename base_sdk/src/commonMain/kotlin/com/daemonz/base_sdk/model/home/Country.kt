package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val id: String?,
    val name: String?,
    val slug: String?
)