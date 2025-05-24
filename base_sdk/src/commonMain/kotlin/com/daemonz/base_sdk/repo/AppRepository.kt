package com.daemonz.base_sdk.repo

import com.daemonz.base_sdk.base.BaseRepository
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.network.IWebService
import com.daemonz.base_sdk.network.WebServiceImpl
import com.daemonz.base_sdk.network.createKtorClient
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.onError
import com.daemonz.base_sdk.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppRepository(): BaseRepository() {
    companion object{
        private const val TAG = "AppRepository"
    }
    private val mWebService: IWebService = WebServiceImpl(createKtorClient())
    fun getMovieBySlug(slug: String, onResultListener: OnResultListener<Item?, Error>) {
        CoroutineScope(Dispatchers.Default).launch {
            mWebService.getMovies(slug).onSuccess { list ->
                onResultListener.onSuccess(list.data.item)
            }.onError { e ->
                onResultListener.onError(e)
            }
        }
    }

     fun getHomeData(onResultListener: OnResultListener<ListData?, Error>) {
        CoroutineScope(Dispatchers.Default).launch {
            mWebService.getHomeData().onSuccess { response ->
                onResultListener.onSuccess(response)
            }.onError { e ->
                onResultListener.onError(e)
            }
        }
    }
}
