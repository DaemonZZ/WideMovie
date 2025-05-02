package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel

class HomeViewModel(
    private val repository: AppRepository
) : BaseViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun getMovie(slug: String) {
        TLog.d(TAG, "getMovie")
        repository.getMovieBySlug(slug)
    }
}