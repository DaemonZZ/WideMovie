package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.KEYWORD
import com.daemonz.base_sdk.SEARCH_PATH
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SearchState(
    val listMovie: ListData? = null,
) : UIState

class SearchViewModel(
    ioScope: CoroutineScope,
    private val repository: AppRepository,
) : BaseViewModel<SearchState, HomeEffect>(
    initialState = SearchState(),
    ioScope = ioScope,
) {

    private val _cartoonState = MutableStateFlow<ListData?>(null)
    val cartoonState = _cartoonState.asStateFlow()

    private val _watchedMovies = MutableStateFlow<ListData?>(null)
    val watchedMovies = _watchedMovies.asStateFlow()

    fun searchMoviesByName(name: String)= launchOnIO {
        val query = mapOf(
            KEYWORD to name.trim()
        )
        TLog.d("SearchViewModel", "query: $query")
        repository.searchMovies(path = SEARCH_PATH, query = query, onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                setState { SearchState(data) }
                TLog.d(TAG, "searchMovies() onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "searchMovies() error: $error")
            }
        })
    }

    fun getCartoon()= launchOnIO {
        val path = "danh-sach/hoat-hinh"
        repository.searchMovies(path = path, onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _cartoonState.value = data
                TLog.d(TAG, "searchMovies() onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "searchMovies() error: $error")
            }
        })
    }
}