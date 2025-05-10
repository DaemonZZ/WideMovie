package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.repo.AppRepository
import com.mp.widemovie.base.BaseViewModel

class HomeViewModel(
    private val repository: AppRepository
) : BaseViewModel() {

    fun getMovie(slug: String) {
        repository.getMovieBySlug(slug)
    }
}