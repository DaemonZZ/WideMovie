package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Episode(
    @SerializedName("server_name") val serverName: String = "",
    @SerializedName("server_data") val serverData: List<EpisodeDetail> = listOf(),
    val pivot: String = "",
) {
    fun getCurrentEpisodeDetail(): EpisodeDetail? {
        return serverData.firstOrNull { it.slug == pivot }
    }
}
