package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    //for showing data
    @SerialName("items") val items: List<Item> = listOf(),
    // for detail data
    @SerialName("item") val item: Item? = null,
    @SerialName("APP_DOMAIN_CDN_IMAGE") val imgDomain: String = "",
    @SerialName("seoOnPage") val seoOnPage: SeoOnPage? = null,
) : NetworkEntity() {
    fun getListUrl(): List<String> = items.map { getImageUrl(it) }
    private fun getImageUrl(item: Item): String {
        return item.getImageUrl(imgDomain)
    }

    fun getImageUrl(): String = seoOnPage?.seoSchema?.image ?: ""
}
