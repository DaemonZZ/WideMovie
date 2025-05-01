package com.daemonz.base_sdk.model

import kotlinx.serialization.SerialName

data class EpisodeDetail(
    @SerialName("name") val name: String = "",
    @SerialName("slug") val slug: String = "",
    @SerialName("filename") val filename: String = "",
    @SerialName("link_embed") val url: String = "",
    @SerialName("link_m3u8") val m3u8: String = "",
)
