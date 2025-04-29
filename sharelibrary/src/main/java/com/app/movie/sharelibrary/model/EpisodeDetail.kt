package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class EpisodeDetail(
    @SerializedName("name") val name: String = "",
    @SerializedName("slug") val slug: String = "",
    @SerializedName("filename") val filename: String = "",
    @SerializedName("link_embed") val url: String = "",
    @SerializedName("link_m3u8") val m3u8: String = "",
)
