package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("server_name") val serverName: String = "",
    @SerialName("server_data") val serverData: List<EpisodeDetail> = listOf(),
    val pivot: String = "",
) : NetworkEntity() {
    fun getCurrentEpisodeDetail(): EpisodeDetail? {
        return serverData.firstOrNull { it.slug == pivot }
    }
}
