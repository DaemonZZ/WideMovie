package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val currentPage: Int?,
    val pageRanges: Int?,
    val totalItems: Int?,
    val totalItemsPerPage: Int?
)