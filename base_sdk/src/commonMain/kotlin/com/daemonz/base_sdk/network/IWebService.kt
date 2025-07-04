package com.daemonz.base_sdk.network

import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.Result

interface IWebService {
    suspend fun getMovies(slug: String): Result<ListData, Error>

    suspend fun getHomeData(): Result<ListData, Error>

    suspend fun getMovieLists(path: String, query: Map<String, String> = emptyMap<String, String>()): Result<ListData, Error>
}