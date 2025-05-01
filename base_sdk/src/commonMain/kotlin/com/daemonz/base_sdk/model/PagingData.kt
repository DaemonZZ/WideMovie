package com.daemonz.base_sdk.model

import kotlinx.serialization.Serializable

@Serializable
data class PagingData<T>(
    val page: Int,
    val data: T
)
