package com.app.movie.sharelibrary.model

import androidx.annotation.Keep

@Keep
data class PagingData<T>(
    val page: Int,
    val data: T
)
