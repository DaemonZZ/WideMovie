package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.repo.AppRepository
import com.mp.widemovie.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(
    private val repository: AppRepository,
) : BaseViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _currentVideo = MutableStateFlow<Item?>(null)
    val currentVideo: StateFlow<Item?> = _currentVideo

    fun loadMovie(slug: String) {
        //Nhin thoi thays chan roi..
    }

}