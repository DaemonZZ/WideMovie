package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.repo.AppRepository
import com.mp.widemovie.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope

class HomeViewModel(
    private val ioScope: CoroutineScope,
    private val repository: AppRepository
) : BaseViewModel(ioScope) {

    fun getMovie(slug: String) {
//        repository.getMovieBySlug(slug)
    }
}