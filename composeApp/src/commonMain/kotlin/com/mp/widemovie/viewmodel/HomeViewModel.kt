package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.PATHS
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIEffect
import com.mp.widemovie.base.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

const val TAG = "HomeViewModel"

data class HomeState(
    val listMovie: List<String> = emptyList(),
) : UIState

sealed class HomeEffect : UIEffect {
    object ShowToast : HomeEffect()
}

class HomeViewModel(
    private val ioScope: CoroutineScope,
    private val repository: AppRepository,
) : BaseViewModel<HomeState, HomeEffect>(
    initialState = HomeState(),
    ioScope = ioScope,
) {

    private val _stateFlowTvShows = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowTvShows = _stateFlowTvShows.asStateFlow()

    private val _stateFlowPhimBo = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowPhimBo = _stateFlowPhimBo.asStateFlow()

    private val _stateFlowCategories = MutableStateFlow<ListData?>(null)
    val stateFlowCategories = _stateFlowCategories.asStateFlow()

    private val _stateFlowMoviesFooter = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowMoviesFooter = _stateFlowMoviesFooter.asStateFlow()

    private val _stateFlowMoviesInComming = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowMoviesInComming = _stateFlowMoviesInComming.asStateFlow()

    private val _stateFlowFilmLe = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowFilmLe = _stateFlowFilmLe.asStateFlow()

    init {
        getIncommingFilm()
        getAllCatergories()
        getPhimBo()
        getTvShows()
        getFilmLe()
    }

    private fun getAllCatergories() = launchOnIO {
        repository.getAllCatergories(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowCategories.value = data
                TLog.d(TAG, "getAllCatergories onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "getAllCatergories error: $error")
            }
        })
    }

    fun getIncommingFilm() =
        launchOnIO {
            repository.searchMovies(
                path = "${PATHS.LIST.id}/${PATHS.INCOMMING.id}",
                onResultListener = object :
                    OnResultListener<ListData?, Error> {
                    override fun onSuccess(data: ListData?) {
                        _stateFlowMoviesInComming.value =
                        data?.data?.items?.sortedBy { it.time } ?: emptyList()
                        TLog.d(TAG, "getMoviesByType onSuccess: $data")
                    }

                    override fun onError(error: Error) {
                        errorMessage.value = error.toString()
                        TLog.e(TAG, "getMoviesByType error: $error")
                    }
                })
        }


    fun getMoviesByType(type: String) =
        launchOnIO {
            repository.searchMovies(
                path = "${PATHS.CATERGORY.id}/$type",
                onResultListener = object :
                    OnResultListener<ListData?, Error> {
                    override fun onSuccess(data: ListData?) {
                        _stateFlowMoviesFooter.value =
                            data?.data?.items?.sortedBy { it.time } ?: emptyList()
                        TLog.d(TAG, "getMoviesByType onSuccess: $data")
                    }

                    override fun onError(error: Error) {
                        errorMessage.value = error.toString()
                        TLog.e(TAG, "getMoviesByType error: $error")
                    }
                })
        }

    private fun getTvShows() = launchOnIO {
        repository.getTvShows(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowTvShows.value = data?.data?.items ?: emptyList()
                TLog.d(TAG, "getTvShows onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "getTvShows error: $error")
            }
        }
        )
    }

    private fun getPhimBo() = launchOnIO {
        repository.getPhimBo(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowPhimBo.value = data?.data?.items ?: emptyList()
                TLog.d(TAG, "getTvShows onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "getTvShows error: $error")
            }
        }
        )
    }

    private fun getFilmLe() = launchOnIO {
        repository.getFilmLe(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowFilmLe.value = data?.data?.items ?: emptyList()
                TLog.d(TAG, "getTvShows onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "getTvShows error: $error")
            }
        }
        )
    }
}