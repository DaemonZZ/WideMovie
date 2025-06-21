package com.mp.widemovie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.daemonz.common.components.buttons.BaseButtonDefault
import com.daemonz.common.components.buttons.BaseOutlinedButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState

@Composable
actual fun PaginatedEpisodeGridWithPageNumbers(
    modifier: Modifier,
    episodeUIStates: EpisodeUIState,
    episodesPerPage: Int,
    onEpisodeClick: (EpisodeDetailUIState) -> Unit,
) {
    val allEpisodes = episodeUIStates.serverData
    val pageCount = (allEpisodes.size + episodesPerPage - 1) / episodesPerPage
    var currentPage by remember { mutableStateOf(0) }
    var selectedEpisode = episodeUIStates.selectedEpisode

    val currentEpisodes = allEpisodes.drop(currentPage * episodesPerPage).take(episodesPerPage)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Tiêu đề
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Danh sách tập ${episodeUIStates.serverName}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Danh sách tập
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp) // giới hạn chiều cao nếu muốn cuộn
        ) {
            itemsIndexed(currentEpisodes) { index, episode ->
                val isSelected = episode == selectedEpisode
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) Color.Red else Color.DarkGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            selectedEpisode = episode
                            onEpisodeClick(episode)
                        }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BaseText(
                        text = episode.title,
                        style = FidoTheme.typography.caption,
                    )
                }
            }
        }

        if (allEpisodes.size > episodesPerPage) {
            Spacer(modifier = Modifier.height(16.dp))

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { if (currentPage > 0) currentPage-- },
                    enabled = currentPage > 0
                ) {
                    Text("Trang trước")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { if (currentPage < pageCount - 1) currentPage++ },
                    enabled = currentPage < pageCount - 1
                ) {
                    Text("Trang sau")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Danh sách số trang
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (0 until pageCount).forEach { index ->
                    val isCurrent = index == currentPage
                    BaseOutlinedButton(
                        onClick = { currentPage = index },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp),
                        colors = if (isCurrent) BaseButtonDefault.buttonPrimaryColors() else BaseButtonDefault.outlinedButtonColors(),
                        shape = FidoTheme.shapes.small,
                    ) {
                        BaseText(text = "${index + 1}")
                    }
                }
            }
        }
    }
}