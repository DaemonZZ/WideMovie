package com.daemonz.base_sdk.model

import kotlinx.serialization.Serializable

@Serializable
data class SelectedFilmInfo(
    val id: String,
    val name: String,
    val quality: String,
    val episodeCurrent: String,
    val time: String,
    val posterUrl: String,
    val thumbUrl: String
){
    fun getTimeInfo() = episodeCurrent + "\n" + time

    fun convertToItem(): Item{
        return Item(
            id = id,
            name = name,
            quality = quality,
            episodeCurrent = episodeCurrent,
            time = time,
            posterUrl = posterUrl,
            thumbUrl = thumbUrl
        )
    }
}
