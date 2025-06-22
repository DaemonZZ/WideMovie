package com.daemonz.base_sdk.repo

import com.daemonz.base_sdk.base.BaseRepository
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.network.IWebService
import com.daemonz.base_sdk.network.WebServiceImpl
import com.daemonz.base_sdk.network.createKtorClient
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.PATHS
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.base_sdk.utils.onError
import com.daemonz.base_sdk.utils.onSuccess

class AppRepository() : BaseRepository() {
    companion object {
        private const val TAG = "AppRepository"
    }

    private val mWebService: IWebService = WebServiceImpl(createKtorClient())
    suspend fun getMovieBySlug(slug: String, onResultListener: OnResultListener<Item?, Error>) {
        mWebService.getMovies(slug).onSuccess { list ->
            onResultListener.onSuccess(list.data?.item)
        }.onError { e ->
            onResultListener.onError(e)
        }

    }

    suspend fun getHomeData(onResultListener: OnResultListener<ListData?, Error>) {
        mWebService.getHomeData().onSuccess { response ->
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
            }
    }

    suspend fun getAllCatergories(onResultListener: OnResultListener<ListData?, Error>) {
            mWebService.getMovieLists(PATHS.CATERGORY.id).onSuccess { response ->
                onResultListener.onSuccess(response)
            }.onError { e ->
                onResultListener.onError(e)
            }
    }

    suspend fun searchMovies(path: String, query: Map<String, String> = emptyMap<String, String>(), onResultListener: OnResultListener<ListData?, Error>) {
            mWebService.getMovieLists(path, query).onSuccess { response ->
                TLog.d("getDataByPath", "path: $path\nonSuccess: $response")
                onResultListener.onSuccess(response)
            }.onError { e ->
                onResultListener.onError(e)
            }
    }
}
