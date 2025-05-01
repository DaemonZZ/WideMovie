package com.daemonz.base_sdk.network

import com.daemonz.base_sdk.BASE_URL
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.utils.NetworkError
import com.daemonz.base_sdk.utils.Result
import com.daemonz.base_sdk.utils.TLog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class WebServiceImpl(
    private val client: HttpClient
) : IWebService {
    companion object {
        private const val TAG = "WebServiceImpl"
    }
    override suspend fun getMovies(slug: String): Result<ListData, NetworkError> {
        val response = try {
            client.get {
                url{
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path("phim/$slug")
                }
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UNKNOWN)
        }
        return when(response.status.value) {
            in 200..299 -> {
                val movieData = response.body<ListData>()
                Result.Success(movieData)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)

        }
    }
}