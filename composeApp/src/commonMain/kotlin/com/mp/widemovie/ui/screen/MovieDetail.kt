package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.common.components.buttons.BaseButtonWithIcon
import com.daemonz.common.components.buttons.BaseIcon
import com.daemonz.common.components.buttons.BaseIconButton
import com.daemonz.common.components.buttons.BaseOutlinedButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.ui.components.BaseTopAppBar
import com.mp.widemovie.ui.uistate.CastMemberUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import com.mp.widemovie.viewmodel.DetailScreenState
import com.mp.widemovie.viewmodel.DetailViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.banner_ex
import widemovie.composeapp.generated.resources.bookmark_add
import widemovie.composeapp.generated.resources.cast_connected
import widemovie.composeapp.generated.resources.ic_backspace
import widemovie.composeapp.generated.resources.ic_download
import widemovie.composeapp.generated.resources.ic_play_circle
import widemovie.composeapp.generated.resources.ic_star
import widemovie.composeapp.generated.resources.share

data class MovieDetail(val slug: String?) : BaseScreen() {
    override val key = "${super.key}?${
        buildQueryFromParams(
            mapOf(
                "film" to slug.toString()
            )
        )
    }"

    @Composable
    override fun Content() = FidoTheme(isDarkTheme = true) {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: DetailViewModel = koinViewModel()
        LaunchedEffect(Unit) {
            viewModel.loadMovie(slug.toString())
        }
        val uiState: DetailScreenState by viewModel.uiState.collectAsState()
        Scaffold(
            modifier = Modifier
                .background(FidoTheme.colorScheme.onBackground)
                .fillMaxSize(),
        ) { innerPadding ->
            val maxImageSize: Dp = 300.dp
            val minImageSize: Dp = 100.dp
            var currentImageSize by remember { mutableStateOf(maxImageSize) }
            var imageScale by remember { mutableFloatStateOf(1f) }

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource,
                    ): Offset {
                        // Calculate the change in image size based on scroll delta
                        val delta = available.y
                        val newImageSize = currentImageSize + delta.dp
                        val previousImageSize = currentImageSize

                        // Constrain the image size within the allowed bounds
                        currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                        val consumed = currentImageSize - previousImageSize

                        // Calculate the scale for the image
                        imageScale = currentImageSize / maxImageSize

                        // Return the consumed scroll amount
                        return Offset(0f, consumed.value)
                    }
                }
            }

            Box(Modifier.nestedScroll(nestedScrollConnection)) {
                BodyContent(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .offset {
                            IntOffset(0, currentImageSize.roundToPx())
                        },
                    contentUIState = uiState.contentUIState,
                    listEpisodeUIState = uiState.listEpisodeUIState,
                    callbacks = BodyContentCallbacks({}, {}, {}, {}),//TODO:
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .align(Alignment.TopCenter)
                        .graphicsLayer {
//                            scaleY = imageScale
//                            // Center the image vertically as it scales
//                            translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                        }
                ) {
                    Image(
                        painter = painterResource(Res.drawable.banner_ex),
                        contentDescription = "Banner",
                        contentScale = ContentScale.Crop,
                    )
                }

            }

            BaseTopAppBar(modifier = Modifier.fillMaxWidth()) {
                BaseIconButton(src = Res.drawable.ic_backspace, onClick = {
                    nav.popUntilRoot()
                })
                Spacer(modifier = Modifier.weight(1f))
                BaseIconButton(src = Res.drawable.cast_connected, onClick = {
                    //TODO: show on TV
                })
            }
        }
    }

    @Composable
    fun BodyContent(
        modifier: Modifier = Modifier,
        contentUIState: ContentUIState = ContentUIState(),
        listEpisodeUIState: List<EpisodeUIState> = emptyList(),
        selectedServer: EpisodeUIState? = null,
        callbacks: BodyContentCallbacks,
    ) {
        Column(modifier = modifier) {
            TitleSection(contentUIState, onPlayClick = { callbacks.onPlayClick() })
            Spacer(modifier = Modifier.height(24.dp))
            GenreAndDescription(contentUIState.genres, contentUIState.description)
            Spacer(modifier = Modifier.height(24.dp))
            CastSection(contentUIState.cast)
            if (listEpisodeUIState.isNotEmpty()) {
                ServerSection(
                    modifier = Modifier,
                    episodeUIStates = listEpisodeUIState,
                    onEpisodeClick = {
                        callbacks.onSelectedServer(it)
                    })
                selectedServer?.let {
                    EpisodesSection(
                        modifier = Modifier,
                        episodeUIStates = it,
                        onEpisodeClick = {

                        })
                }
            }
            MovieTabScreen(modifier = Modifier.padding(6.dp))
        }
    }

    @Composable
    fun TitleSection(content: ContentUIState, onPlayClick: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BaseText(content.title, style = FidoTheme.typography.h1)
            Spacer(Modifier.weight(1f))
            BaseIconButton(src = Res.drawable.bookmark_add, onClick = {})
            BaseIconButton(src = Res.drawable.share, onClick = {})
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            BaseIconButton(
                src = Res.drawable.ic_star,
                contentDescription = null,
                tintColor = FidoTheme.colorScheme.PrimaryMain,
                onClick = {
                    //TODO
                }
            )
            BaseText("${content.rating}", style = FidoTheme.typography.caption)
            BaseText(
                " • ${content.releaseYear}",
                modifier = Modifier.padding(start = 8.dp),
                style = FidoTheme.typography.caption
            )
            Spacer(modifier = Modifier.weight(1f))
            BaseOutlinedButton(
                modifier = Modifier,
                contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp),
                onClick = {}
            ) {
                BaseText(
                    text = content.ageRating,
                    maxLines = 1,
                    style = FidoTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            BaseOutlinedButton(
                modifier = Modifier,
                contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp),
                onClick = {}
            ) {
                BaseText(text = content.country, maxLines = 1, style = FidoTheme.typography.caption)
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (content.hasSubtitle) {
                BaseOutlinedButton(
                    modifier = Modifier,
                    contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp),
                    onClick = {}
                ) {
                    BaseText(text = "Subtitle", maxLines = 1, style = FidoTheme.typography.caption)
                }
            }
        }
        Spacer(modifier = Modifier.height(13.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BaseButtonWithIcon(
                modifier = Modifier.weight(0.5f),
                onClick = onPlayClick,
                icon = painterResource(Res.drawable.ic_play_circle),
                text = "Play"
            )
            Spacer(modifier = Modifier.width(8.dp))
            BaseOutlinedButton(
                modifier = Modifier.weight(0.5f),
                onClick = { /* TODO: Download */ },
            ) {
                BaseIcon(src = Res.drawable.ic_download)
                BaseText(
                    "Download",
                    modifier = Modifier.padding(start = 4.dp),
                    maxLines = 1,
                    style = FidoTheme.typography.h1
                )
            }
        }
    }

    @Composable
    fun GenreAndDescription(genres: List<String>, description: String) {
        BaseText("Genre: ${genres.joinToString(", ")}", style = FidoTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(8.dp))
        BaseText(description, style = FidoTheme.typography.subtitle2)
    }

    @Composable
    fun CastSection(cast: List<CastMemberUIState>) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            BaseText("Cast", style = FidoTheme.typography.subtitle1)
            BaseText("See more", style = FidoTheme.typography.subtitle2)
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            itemsIndexed(cast) { index, member ->
                Column(
                    modifier = Modifier.padding(12.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    BaseText(
                        text = member.name,
                        color = FidoTheme.colorScheme.PrimaryMain,
                        style = FidoTheme.typography.h6,
                    )
                    BaseText(
                        text = member.role,
                        style = FidoTheme.typography.h6,
                    )
                }
            }
        }
    }

    @Composable
    fun ServerSection(
        modifier: Modifier = Modifier,
        episodeUIStates: List<EpisodeUIState>,
        onEpisodeClick: (EpisodeUIState) -> Unit,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseText(
                    text = "Server",
                    style = FidoTheme.typography.subtitle1,
                )

                BaseText(
                    text = "See more",
                    style = FidoTheme.typography.subtitle2,
                )
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(episodeUIStates) { indexKey, item ->
                    BaseOutlinedButton(
                        modifier = Modifier,
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 4.dp),
                        onClick = {
                            onEpisodeClick.invoke(item)
                        }
                    ) {
                        BaseText(
                            text = item.serverName,
                            maxLines = 1,
                            style = FidoTheme.typography.caption
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun EpisodesSection(
        modifier: Modifier = Modifier,
        episodeUIStates: EpisodeUIState,
        onEpisodeClick: (EpisodeDetailUIState) -> Unit,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseText(
                    text = "Episodes",
                    style = FidoTheme.typography.subtitle1,
                )

                BaseText(
                    text = "See more",
                    style = FidoTheme.typography.subtitle2,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(episodeUIStates.serverData) { indexKey, item ->
                    EpisodeCard(
                        episodeDetailUIState = item,
                        onClick = { onEpisodeClick.invoke(item) })
                }
            }
        }
    }

    @Composable
    fun EpisodeCard(
        episodeDetailUIState: EpisodeDetailUIState,
        onClick: (EpisodeDetailUIState) -> Unit,
    ) {
        Card(
            modifier = Modifier
                .width(140.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FidoTheme.colorScheme.Neutral_TextColorsGreyPurpleBlack)
                .clickable(enabled = true, onClick = { onClick.invoke(episodeDetailUIState) }),
            shape = FidoTheme.shapes.medium,
        ) {
            episodeDetailUIState.thumbnailUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = episodeDetailUIState.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Play Icon Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(7.dp)
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                BaseIcon(
                    src = Res.drawable.ic_play_circle,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp).align(Alignment.Center)
                )
                BaseText(
                    text = episodeDetailUIState.title,
                    style = FidoTheme.typography.caption,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    data class BodyContentCallbacks(
        val onItemClick: (String) -> Unit,
        val onRefresh: () -> Unit,
        val onPlayClick: () -> Unit,
        val onSelectedServer: (EpisodeUIState) -> Unit,
    )
}
