package com.daemonz.base_sdk.repo

import com.daemonz.base_sdk.base.BaseRepository
import com.daemonz.base_sdk.network.IWebService
import com.daemonz.base_sdk.network.WebServiceImpl
import com.daemonz.base_sdk.network.createKtorClient
import com.daemonz.base_sdk.utils.TLog
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
    fun getMovieBySlug(slug: String) {
        CoroutineScope(Dispatchers.Default).launch {
            mWebService.getMovies(slug).onSuccess { list ->
                TLog.d(TAG, list.toString())
            }.onError { e ->
                TLog.e(TAG, e.toString())
            }
        }

    }
}
