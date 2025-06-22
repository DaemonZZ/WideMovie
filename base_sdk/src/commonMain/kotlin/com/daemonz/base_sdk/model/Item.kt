package com.daemonz.base_sdk.model

import com.daemonz.base_sdk.base.NetworkEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("_id") val id: String,
    @SerialName("name") val name: String = "",
    @SerialName("content") val content: String = "",
    @SerialName("origin_name") val originName: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("thumb_url") val thumbUrl: String = "",
    @SerialName("poster_url") val posterUrl: String = "",
    @SerialName("time") val time: String = "",
    @SerialName("episode_current") val episodeCurrent: String = "",
    @SerialName("quality") val quality: String = "",
    @SerialName("lang") val language: String = "",
    @SerialName("year") val year: String = "",
    @SerialName("category") val category: List<Category> = listOf(),
    @SerialName("country") val country: List<Country> = listOf(),
    @SerialName("slug") val slug: String = "",
    @SerialName("status") val status: String = "",
    @SerialName("episodes") val episodes: List<Episode> = listOf(),
    @SerialName("actor") val actor: List<String> = listOf(),
    @SerialName("director") val director: List<String> = listOf(),
    @SerialName("episode_total") val episodeTotal: String = "",
    @SerialName("trailer_url") val trailerUrl: String = "",
    @SerialName("imdb") val imdb: Imdb? = null,
    @SerialName("modified") val modified: Modified?= null,
    @SerialName("sub_docquyen") val subUnique: Boolean?= null,
    @SerialName("tmdb") val tmd: Tmdb?= null,
    @SerialName("rating") var rating: Double = 0.0
) : NetworkEntity() {
    fun getImageUrl(imgDomain: String): String {
        return "$imgDomain/uploads/movies/$thumbUrl"
    }
}
