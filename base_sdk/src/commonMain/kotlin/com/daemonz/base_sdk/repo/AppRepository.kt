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

    suspend fun getAllCatergories(onResultListener: OnResultListener<ListData?, Error>) {
        mWebService.getMovieLists(PATHS.CATERGORY.id).onSuccess { response ->
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
        }
    }

    suspend fun searchMovies(
        path: String,
        query: Map<String, String> = emptyMap<String, String>(),
        onResultListener: OnResultListener<ListData?, Error>
    ) {
        mWebService.getMovieLists(path, query).onSuccess { response ->
            TLog.d(TAG, "func searchMovies\n path: $path\nonSuccess: $response")
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
        }
    }

    suspend fun getTvShows(onResultListener: OnResultListener<ListData?, Error>) {
        val path =  PATHS.LIST.id + "/" + PATHS.TV_SHOWS.id
        mWebService.getMovieLists(path).onSuccess { response ->
            TLog.d(TAG, "func getTvShows\n path: $path\nonSuccess: $response")
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
        }
    }

    suspend fun getPhimBo(onResultListener: OnResultListener<ListData?, Error>) {
        val path =  PATHS.LIST.id + "/" + PATHS.PHIM_BO.id
        mWebService.getMovieLists(path).onSuccess { response ->
            TLog.d(TAG, "func getPhimBo\n path: $path\nonSuccess: $response")
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
        }
    }

    suspend fun getFilmLe(onResultListener: OnResultListener<ListData?, Error>) {
        val path =  PATHS.LIST.id + "/" + PATHS.PHIM_LE.id
        mWebService.getMovieLists(path).onSuccess { response ->
            TLog.d("getFilmLe", "path: ${PATHS.PHIM_LE.id}: $response")
            TLog.d(TAG, "func getFilmLe\n path: $path\nonSuccess: $response")
            onResultListener.onSuccess(response)
        }.onError { e ->
            onResultListener.onError(e)
        }
    }
}
