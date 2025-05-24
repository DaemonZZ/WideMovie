package com.mp.widemovie.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UIState, Effect : UIEffect>(
    initialState: State,
    private val ioScope: CoroutineScope,
) : ViewModel() {
    private val TAG = this::class.simpleName

    // UI state (StateFlow for UI state collection)
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    // One-time effects (Channel + Flow)
    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    var errorMessage = MutableStateFlow<String?>(null)
    var otherMessage = MutableStateFlow<String?>(null)

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        errorMessage.value = throwable.message
    }

    protected fun setState(reducer: State.() -> State) {
        _uiState.update { it.reducer() }
    }

    protected fun sendEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            try {
                block()
            } catch (ex: Exception) {
                errorMessage.emit("ERROR ${ex.message}")
            }
        }
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return ioScope.launch(exceptionHandler) {
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

interface UIState
interface UIEffect