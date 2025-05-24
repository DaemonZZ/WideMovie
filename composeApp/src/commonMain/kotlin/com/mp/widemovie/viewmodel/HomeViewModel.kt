package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.IMG_BASE_URL
import com.daemonz.base_sdk.model.Data
import com.daemonz.base_sdk.model.home.HomeResponse
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

const val TAG = "HomeViewModel"

class HomeViewModel(
    private val ioScope: CoroutineScope,
    private val repository: AppRepository
) : BaseViewModel(ioScope) {

    private val _stateFlowUI = MutableStateFlow<HomeResponse?>(null)
    val stateFlowUI = _stateFlowUI.asStateFlow()

    fun getHomeData() {
        repository.getHomeData(onResultListener = object :
            OnResultListener<HomeResponse?, Error> {
            override fun onSuccess(data: HomeResponse?) {
                _stateFlowUI.value = data
                TLog.d(TAG, "onSuccess: $data")
            }

            override fun onError(error: Error) {
                TLog.d(TAG, "error: $error")
            }
        }
        )
    }
}