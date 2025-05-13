package com.mp.widemovie.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val ioScope: CoroutineScope
) : ViewModel() {
    private val TAG = this::class.simpleName
    protected val _state = MutableStateFlow("Initial")
    val state: StateFlow<String> get() = _state
    var errorMessage = MutableStateFlow<String?>(null)
    var otherMessage = MutableStateFlow<String?>(null)

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        errorMessage.value = throwable.message
    }
    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch (exceptionHandler){
            try {
                block()
            } catch (ex: Exception) {
                errorMessage.emit("ERROR ${ex.message}")
            }
        }
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return ioScope.launch (exceptionHandler){
            try {
                block()
            } catch (ex: Exception) {
                ex.printStackTrace()
                errorMessage.emit("launchOnIO ERROR ${ex.message}")
            }
        }
    }

    fun <T> asyncOnIO(block: suspend CoroutineScope.() -> T): Deferred<T> =
        ioScope.async { block() }


    suspend fun <T> asyncOnIOAwait(block: suspend CoroutineScope.() -> T): T =
        asyncOnIO(block).await()
}