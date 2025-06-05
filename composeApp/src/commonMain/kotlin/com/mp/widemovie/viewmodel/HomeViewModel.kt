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

    private val _stateFlowHome = MutableStateFlow<ListData?>(null)
    val stateFlowHome = _stateFlowHome.asStateFlow()

    private val _stateFlowCategories = MutableStateFlow<ListData?>(null)
    val stateFlowCategories = _stateFlowCategories.asStateFlow()

    private val _stateFlowMoviesFooter = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowMoviesFooter = _stateFlowMoviesFooter.asStateFlow()

    private val _stateFlowMoviesInComming = MutableStateFlow<List<Item>>(emptyList())
    val stateFlowMoviesInComming = _stateFlowMoviesInComming.asStateFlow()

    init {
        getHomeData()
        getAllCatergories()
        getIncommingFilm()
    }

    private fun getHomeData() = launchOnIO {
        repository.getHomeData(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowHome.value = data
                TLog.d(TAG, "onSuccess: $data")
            }

            override fun onError(error: Error) {
                errorMessage.value = error.toString()
                TLog.e(TAG, "getHomeData error: $error")
            }
        }
        )
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


    fun getMoviesByType(type: String) =
        launchOnIO {
            repository.getDataByPath(
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


    fun getIncommingFilm() =
        launchOnIO {
            repository.getDataByPath(
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
}