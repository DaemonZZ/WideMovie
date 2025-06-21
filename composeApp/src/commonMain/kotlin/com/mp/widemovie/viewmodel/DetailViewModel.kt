package com.mp.widemovie.viewmodel

import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.model.ListData
import com.daemonz.base_sdk.repo.AppRepository
import com.daemonz.base_sdk.utils.Error
import com.daemonz.base_sdk.utils.OnResultListener
import com.daemonz.base_sdk.utils.PATHS
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseViewModel
import com.mp.widemovie.base.UIEffect
import com.mp.widemovie.base.UIState
import com.mp.widemovie.ui.mapping.toContentUIState
import com.mp.widemovie.ui.mapping.toEpisodeUIState
import com.mp.widemovie.ui.mapping.toMovieCardUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import com.mp.widemovie.ui.uistate.MovieCardUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

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
    private val _listRelatedMovie = MutableStateFlow<List<MovieCardUIState>>(emptyList())
    val lisRelatedMovie: StateFlow<List<MovieCardUIState>> = _listRelatedMovie.asStateFlow()

    private val _selectedServer: MutableStateFlow<EpisodeUIState?> = MutableStateFlow(null)
    val selectedServer: StateFlow<EpisodeUIState?> = _selectedServer.asStateFlow()

    private fun fetchData(item: Item) {
        val list = item.episodes.map { it.toEpisodeUIState() }
        _selectedServer.value = list.firstOrNull()
        setState {
            copy(
                contentUIState = item.toContentUIState(),
                listEpisodeUIState = list
            )
        }
    }

    fun loadMovie(slug: String) = launchOnIO {
        repository.getMovieBySlug(slug, onResultListener = object : OnResultListener<Item?, Error> {
            override fun onSuccess(data: Item?) {
                if (currentVideo.value != data) {
                    currentVideo.value = data
                    if (data != null) {
                        fetchData(data)
                        val category = data.category.firstOrNull()?.slug
                        val country = data.country.firstOrNull()?.slug
                        if (country != null && category != null) {
                            getRelatedMovies(category, country)
                        }
                    } else {
                        //TODO: Loading state
                    }
                }
            }

            override fun onError(error: Error) {
                //TODO: Error state
            }
        })
    }

    fun getRelatedMovies(category: String, country: String) =
        launchOnIO {
            repository.searchMovies(
                path = PATHS.LIST.id,
                query = mapOf(Pair("category", category), Pair("country", country), Pair("page", Random.nextInt(1, 11).toString())),
                onResultListener = object :
                    OnResultListener<ListData?, Error> {
                    override fun onSuccess(data: ListData?) {
                        val list = data?.data?.items?.sortedBy { it.time } ?: emptyList()
                        _listRelatedMovie.value =
                            list.filter { it.slug != currentVideo.value?.slug }
                                .map { it.toMovieCardUIState() }
                        TLog.d(TAG, "getRelatedMovies onSuccess: $data")
                    }

                    override fun onError(error: Error) {
                        _listRelatedMovie.value = emptyList()
                        errorMessage.value = error.toString()
                        TLog.e(TAG, "getRelatedMovies error: $error")
                    }
                })
        }

    fun selectServer(server: EpisodeUIState?) {
        _selectedServer.value = server
    }

    fun selectEpisode(item: EpisodeDetailUIState) {
        val newState = selectedServer.value?.copy(selectedEpisode = item)
        _selectedServer.value = newState
    }

}