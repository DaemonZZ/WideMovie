package com.daemonz.base_sdk.model

data class PagingData<T>(
    val page: Int,
    val data: T
)
