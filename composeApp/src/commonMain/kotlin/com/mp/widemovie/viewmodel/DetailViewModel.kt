package com.mp.widemovie.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.ui.mapping.toContentUIState
import com.mp.widemovie.ui.mapping.toEpisodeUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import com.mp.widemovie.utils.CacheFileEvent
import com.mp.widemovie.utils.VideoFileMaker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AppRepository,
    private val ioScope: CoroutineScope,
) : BaseViewModel(ioScope) {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val currentVideo = MutableStateFlow<Item?>(null)

    private val _testVideo = MutableStateFlow<Pair<String, String>>(Pair("", ""))
    val testVideo: StateFlow<Pair<String, String>> = _testVideo

    val contentUIState: StateFlow<ContentUIState> = currentVideo.mapNotNull { item ->
        item?.toContentUIState()
    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(0), ContentUIState())

    private val _selectedServer: MutableStateFlow<EpisodeUIState?> = MutableStateFlow(null)
    val selectedServer: StateFlow<EpisodeUIState?> = _selectedServer.asStateFlow()

    val listEpisodeUIState: StateFlow<List<EpisodeUIState>> =
        currentVideo.mapNotNull { item ->
            item?.episodes?.map {
                it.toEpisodeUIState()
            } ?: emptyList()
        }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(0), emptyList())

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

    init {
        launchOnUI {
            listEpisodeUIState.collectLatest {
                //each time change
                selectServer(it.firstOrNull())
            }
        }
    }

    private val videoFileMaker = VideoFileMaker(cacheFileEvent, ioScope)
    fun loadMovie(slug: String) {
        repository.getMovieBySlug(slug, onResultListener = object : OnResultListener<Item?, Error> {
            override fun onSuccess(data: Item?) {
                launchOnIO {
                    data?.let {
                        val randomEp = it.episodes.random().serverData.random()
                        videoFileMaker.setM3U8Url(randomEp.m3u8)
                        videoFileMaker.setSlug("${slug}_${randomEp.slug}")
                        videoFileMaker.convertFile()
                        currentVideo.value = it
                    }
                }
            }

            override fun onError(error: Error) {
            }

        })
    }

    fun selectServer(server: EpisodeUIState?) {
        _selectedServer.value = server
    }

}