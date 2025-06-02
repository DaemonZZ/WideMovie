package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIEffect
import com.mp.widemovie.base.UIState
import com.mp.widemovie.ui.mapping.toContentUIState
import com.mp.widemovie.ui.mapping.toEpisodeUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DetailScreenState(
    val contentUIState: ContentUIState = ContentUIState(),
    val listEpisodeUIState: List<EpisodeUIState> = emptyList(),
) : UIState

sealed class DetailScreenEffect : UIEffect {
    object ShowToast : DetailScreenEffect()
}

class DetailViewModel(
    private val repository: AppRepository,
    private val ioScope: CoroutineScope,
) : BaseViewModel<DetailScreenState, DetailScreenEffect>(
    initialState = DetailScreenState(),
    ioScope = ioScope
) {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val currentVideo = MutableStateFlow<Item?>(null)

    private val _selectedServer: MutableStateFlow<EpisodeUIState?> = MutableStateFlow(null)
    val selectedServer: StateFlow<EpisodeUIState?> = _selectedServer.asStateFlow()

    private fun fetchData(item: Item) {
        val list = item.episodes.map { it.toEpisodeUIState() }
        setState {
            copy(
                contentUIState = item.toContentUIState(),
                listEpisodeUIState = list
            )
        }
        selectServer(list.firstOrNull())
    }

    fun loadMovie(slug: String) = launchOnIO {
        repository.getMovieBySlug(slug, onResultListener = object : OnResultListener<Item?, Error> {
            override fun onSuccess(data: Item?) {
                currentVideo.value = data
                if (data != null) {
                    fetchData(data)
                } else {
                    //TODO: Loading state
                }
            }

            override fun onError(error: Error) {
                //TODO: Error state
            }
        })
    }

    fun selectServer(server: EpisodeUIState?) {
        _selectedServer.value = server
    }

}