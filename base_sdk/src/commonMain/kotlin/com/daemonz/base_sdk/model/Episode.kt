package com.daemonz.base_sdk.model

import kotlinx.serialization.SerialName

data class Episode(
    @SerialName("server_name") val serverName: String = "",
    @SerialName("server_data") val serverData: List<EpisodeDetail> = listOf(),
    val pivot: String = "",
) {
    fun getCurrentEpisodeDetail(): EpisodeDetail? {
        return serverData.firstOrNull { it.slug == pivot }
    }
}
