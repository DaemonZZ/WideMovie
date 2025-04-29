package com.app.movie.sharelibrary.model

import androidx.annotation.Keep
import java.util.Date

@Keep
data class UpdateData(
    val version: String = "",
    @field:JvmField
    val isOptional: Boolean = false,
    val time: Date? = null,
)