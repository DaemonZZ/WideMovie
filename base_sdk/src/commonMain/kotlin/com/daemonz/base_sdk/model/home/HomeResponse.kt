package com.daemonz.base_sdk.model.home

import com.daemonz.base_sdk.IMG_BASE_URL
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val data: Data?,
    val message: String?,
    val status: String?
){
    fun getItemThumbs(): List<String>{
        return data?.items?.map { it.thumbUrl ?: "" } ?: emptyList()
    }

    fun getOgThumbs(): List<String>{
        return data?.seoOnPage?.ogImage?.map { IMG_BASE_URL + it } ?: emptyList()
    }
}