package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.app.movie.sharelibrary.base.NetworkEntity
import com.google.gson.annotations.SerializedName

@Keep
data class Category(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String
) : NetworkEntity()
