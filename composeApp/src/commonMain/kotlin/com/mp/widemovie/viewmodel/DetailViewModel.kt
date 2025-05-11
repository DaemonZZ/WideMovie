package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.utils.CacheFileEvent
import com.mp.widemovie.utils.VideoFileMaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(
    private val repository: AppRepository,
    private val ioScope: CoroutineScope
) : BaseViewModel(ioScope) {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _currentVideo = MutableStateFlow<Item?>(null)
    val currentVideo: StateFlow<Item?> = _currentVideo
    private val _testVideo = MutableStateFlow<Pair<String, String>>(Pair("", ""))
    val testVideo: StateFlow<Pair<String, String>> = _testVideo

    private val cacheFileEvent: CacheFileEvent = object : CacheFileEvent {
        override fun onCachedFileCreated(file: String, slug: String) {
            TLog.d(TAG, "onCachedFileCreated: $file")
            launchOnUI {
                _testVideo.emit(Pair(file, slug))

            }
        }

        override fun onCachedError(e: String) {
            TLog.e(TAG, e)
        }

    }
    private val videoFileMaker = VideoFileMaker(cacheFileEvent,ioScope)
    fun loadMovie(slug: String) {
        repository.getMovieBySlug(slug, onResultListener = object : OnResultListener<Item?, Error> {
            override fun onSuccess(data: Item?) {
                launchOnIO {
                    data?.let {
                        val randomEp = it.episodes.random().serverData.random()
                        videoFileMaker.setM3U8Url(randomEp.m3u8)
                        videoFileMaker.setSlug("${slug}_${randomEp.slug}")
                        videoFileMaker.convertFile()
                    }
                }
            }

            override fun onError(error: Error) {
            }

        })
    }

}