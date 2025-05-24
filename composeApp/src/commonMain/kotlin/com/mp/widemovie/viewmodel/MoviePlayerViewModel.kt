package com.mp.widemovie.viewmodel

import androidx.compose.ui.geometry.Rect
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIEffect
import com.mp.widemovie.base.UIState
import com.mp.widemovie.utils.CacheFileEvent
import com.mp.widemovie.utils.VideoFileMaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MoviePlayerState(
    val playerVersion: Float = 1.0f,
    val videoUrl: String = "",
    val videoTime: Long = 0L,
    val videoDuration: Long = 0L,
    val videoResolution: Rect = Rect(0f, 0f, 0f, 0f),
) : UIState

sealed class MoviePlayerEffect : UIEffect {
    object ShowToast : MoviePlayerEffect()
}

class MoviePlayerViewModel(
    private val ioScope: CoroutineScope,
) : BaseViewModel<MoviePlayerState, MoviePlayerEffect>(
    initialState = MoviePlayerState(),
    ioScope = ioScope,
) {
    companion object {
        private const val TAG = "VideoPlayerViewModel"
    }

    private val _testVideo = MutableStateFlow<Pair<String, String>?>(null)
    val testVideo: StateFlow<Pair<String, String>?> = _testVideo

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

    private val videoFileMaker = VideoFileMaker(cacheFileEvent, ioScope)

    fun prepareVideo(url: String, slug: String) = launchOnIO {
        videoFileMaker.setM3U8Url(url)
        videoFileMaker.setSlug(slug)
        videoFileMaker.convertFile()
    }

}