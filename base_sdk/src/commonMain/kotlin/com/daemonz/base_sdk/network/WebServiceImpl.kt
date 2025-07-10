package com.daemonz.base_sdk.network

import com.daemonz.base_sdk.BASE_URL
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.NetworkError
import com.daemonz.base_sdk.utils.PATHS
import com.daemonz.base_sdk.utils.Result
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.base_sdk.utils.map
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class WebServiceImpl(
    private val client: HttpClient
) : IWebService {
    companion object {
        private const val TAG = "WebServiceImpl"
    }

    private suspend fun handleApiCall(httpResponse: suspend () -> HttpResponse): Result<HttpResponse, Error> {
        return runCatching {
            Result.Success(httpResponse.invoke())
        }.fold(
            onSuccess = { it },
            onFailure = { throwable ->
                when(throwable) {
                    is UnresolvedAddressException,is IOException -> Result.Error(NetworkError.NO_INTERNET)
                    is ClientRequestException -> Result.Error(NetworkError.WRONG_REQUEST)
                    is ServerResponseException -> Result.Error(NetworkError.SERVER_ERROR)
                    is SerializationException -> Result.Error(NetworkError.SERIALIZATION)
                    is IOException -> Result.Error(NetworkError.NO_INTERNET)
                    else -> Result.Error(NetworkError.UNKNOWN)
                }
            }
        )
    }

    override suspend fun getMovies(slug: String): Result<ListData, Error> {
        return handleApiCall {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path("phim/$slug")
                }
            }
        }.map { data ->
            data.body()
        }
    }

    override suspend fun getHomeData(): Result<ListData, Error> {
        return handleApiCall {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path(PATHS.HOME.id)
                }
            }
        }.map { data ->
            data.body()
        }
    }

    override suspend fun getMovieLists(path: String, query: Map<String, String>): Result<ListData, Error> {
        TLog.d(TAG, "getMovieLists path: $BASE_URL/$path$query")
        return handleApiCall {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path(path)
                    if(query.isNotEmpty()){
                        query.entries.forEach { entry ->
                            parameters.append(entry.key, entry.value)
                        }
                    }
                }
            }
        }.map { data ->
            data.body()
        }
    }
}