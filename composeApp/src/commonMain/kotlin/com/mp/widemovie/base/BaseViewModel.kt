package com.mp.widemovie.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _state = MutableStateFlow("Initial")
    val state: StateFlow<String> get() = _state
}