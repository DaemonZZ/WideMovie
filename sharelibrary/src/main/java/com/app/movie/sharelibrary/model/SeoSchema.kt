package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeoSchema(
    @SerializedName("image") val image: String = "",
)