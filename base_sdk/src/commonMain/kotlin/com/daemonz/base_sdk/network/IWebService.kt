package com.daemonz.base_sdk.network

import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.utils.NetworkError
import com.daemonz.base_sdk.utils.Result

interface IWebService {
    suspend fun getMovies(slug: String): Result<ListData, NetworkError>
}