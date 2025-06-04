package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListData(
    @SerialName("data") val data: Data? = null,
    @SerialName("status") val status: String = "",
    @SerialName("message") val message: String = ""
) : NetworkEntity(){
    fun getFilmCategories(): Map<String, String>? {
        return data?.items?.associate {
            it.name to it.slug
        }
    }
}
