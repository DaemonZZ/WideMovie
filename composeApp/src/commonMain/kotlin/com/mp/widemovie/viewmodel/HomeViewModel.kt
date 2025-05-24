package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.IMG_BASE_URL
import com.daemonz.base_sdk.model.Data
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
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

    private val _stateFlowUI = MutableStateFlow<ListData?>(null)
    val stateFlowUI = _stateFlowUI.asStateFlow()

    init {
        getHomeData()
    }
    fun getHomeData() {
        repository.getHomeData(onResultListener = object :
            OnResultListener<ListData?, Error> {
            override fun onSuccess(data: ListData?) {
                _stateFlowUI.value = data
                TLog.d(TAG, "onSuccess: $data")
            }

            override  fun onError(error: Error) {
                launchOnIO {
                    errorMessage.emit(error.toString())
                }
                TLog.d(TAG, "error: $error")
            }
        }
        )
    }
}