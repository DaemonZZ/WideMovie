package com.daemonz.base_sdk.model.home

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Item(
    @SerialName("_id")
    val id: String?,
    val category: List<Category>?,
    val country: List<Country>?,
    @SerialName("episode_current")
    val episodeCurrent: String?,
    val imdb: Imdb?,
    val lang: String?,
    val modified: Modified?,
    val name: String?,
    @SerialName("origin_name")
    val originName: String?,
    val quality: String?,
    val slug: String?,
    @SerialName("sub_docquyen")
    val subUnique: Boolean?,
    @SerialName("thumb_url")
    val thumbUrl: String?,
    val time: String?,
    @SerialName("tmdb")
    val tmd: Tmdb?,
    val type: String?,
    val year: Int?
)