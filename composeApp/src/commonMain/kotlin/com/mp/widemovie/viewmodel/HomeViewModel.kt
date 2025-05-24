package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.repo.AppRepository
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIEffect
import com.mp.widemovie.base.UIState
import kotlinx.coroutines.CoroutineScope

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

    fun getMovie(slug: String) {
//        repository.getMovieBySlug(slug)
    }
}