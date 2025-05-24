package com.daemonz.base_sdk.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("APP_DOMAIN_CDN_IMAGE")
    val appDomainConImage: String?,
    @SerialName("APP_DOMAIN_FRONTEND")
    val appDomainFrontend: String?,
    val items: List<Item>?,
    val itemsSportsVideos: List<String>?,
    val params: Params?,
    val seoOnPage: SeoOnPage?,
    @SerialName("type_list")
    val typeList: String?
)