package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import com.app.movie.sharelibrary.base.NetworkEntity
import com.google.gson.annotations.SerializedName

@Keep
data class ListData(
    @SerializedName("data") val data: Data,
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = ""
) : NetworkEntity()
