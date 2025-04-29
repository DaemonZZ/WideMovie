package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.app.movie.sharelibrary.base.NetworkEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Item(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("origin_name") val originName: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("thumb_url") val thumbUrl: String = "",
    @SerializedName("poster_url") val posterUrl: String = "",
    @SerializedName("time") val time: String = "",
    @SerializedName("episode_current") val episodeCurrent: String = "",
    @SerializedName("quality") val quality: String = "",
    @SerializedName("lang") val language: String = "",
    @SerializedName("year") val year: String = "",
    @SerializedName("category") val category: List<Category> = listOf(),
    @SerializedName("country") val country: List<Country> = listOf(),
    @SerializedName("slug") val slug: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("episodes") val episodes: List<Episode> = listOf(),
    @SerializedName("actor") val actor: List<String> = listOf(),
    @SerializedName("director") val director: List<String> = listOf(),
    @SerializedName("episode_total") val episodeTotal: String = "",
    @SerializedName("trailer_url") val trailerUrl: String = "",
    var rating: Double = 0.0
) : NetworkEntity(), Serializable {
    fun getImageUrl(imgDomain: String): String {
        return "$imgDomain/uploads/movies/$thumbUrl"
    }
}
