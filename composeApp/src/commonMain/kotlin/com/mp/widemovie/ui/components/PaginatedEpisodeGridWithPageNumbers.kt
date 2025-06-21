package com.mp.widemovie.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState

@Composable
expect fun PaginatedEpisodeGridWithPageNumbers(
    modifier: Modifier = Modifier,
    episodeUIStates: EpisodeUIState,
    episodesPerPage: Int = 49,
    onEpisodeClick: (EpisodeDetailUIState) -> Unit,
)