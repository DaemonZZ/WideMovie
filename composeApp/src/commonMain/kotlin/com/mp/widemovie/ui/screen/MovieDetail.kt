package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.buttons.BaseButtonWithIcon
import com.daemonz.common.components.buttons.BaseIconButton
import com.daemonz.common.components.buttons.BaseOutlinedButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.ui.components.BaseTopAppBar
import com.mp.widemovie.ui.components.PaginatedEpisodeGridWithPageNumbers
import com.mp.widemovie.ui.uistate.CastMemberUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import com.mp.widemovie.ui.uistate.MovieCardUIState
import com.mp.widemovie.viewmodel.DetailScreenState
import com.mp.widemovie.viewmodel.DetailViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.bookmark_add
import widemovie.composeapp.generated.resources.cast_connected
import widemovie.composeapp.generated.resources.ic_backspace
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
        val selectedServer: EpisodeUIState? by viewModel.selectedServer.collectAsState()
        val listRelatedMovie: List<MovieCardUIState> by viewModel.lisRelatedMovie.collectAsState()

        Scaffold(
            modifier = Modifier
                .background(FidoTheme.colorScheme.onBackground)
                .fillMaxSize(),
        ) { innerPadding ->
            val scrollScreen = rememberScrollState()
            BoxWithConstraints(
                modifier = Modifier.verticalScroll(scrollScreen)
                    .background(FidoTheme.colorScheme.onBackground)
            ) {
                //Banner
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .drawWithContent {
                            drawContent() // vẽ ảnh gốc
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Black, Color.Transparent),
                                    startY = 0f,
                                    endY = size.height
                                ),
                                blendMode = BlendMode.DstIn // giữ phần trên rõ, dưới mờ dần
                            )
                        },
                    painter = rememberAsyncImagePainter(uiState.contentUIState.posterUrl),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                )
                BodyContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = if (maxWidth > 500.dp) 150.dp else 16.dp,
                            vertical = if (maxWidth > 500.dp) 150.dp else 100.dp
                        ),
                    contentUIState = uiState.contentUIState,
                    listEpisodeUIState = uiState.listEpisodeUIState,
                    callbacks = BodyContentCallbacks(
                        onPlayClick = {
                            val item = viewModel.selectedServer.value?.serverData?.firstOrNull()
                            if (item?.m3u8Url != null) {
                                nav += MoviePlayerScreen(
                                    slug = "${slug}_${item.slug}",
                                    url = item.m3u8Url,
                                )
                            } else {
                                TLog.e(TAG, "item?.m3u8Url null")
                            }
                        },
                        onEpisodeClick = { item ->
                            if (item.m3u8Url != null) {
                                viewModel.selectEpisode(item)
                                nav += MoviePlayerScreen(
                                    slug = "${slug}_${item.slug}",
                                    url = item.m3u8Url,
                                )
                            } else {
                                TLog.e(TAG, "item?.m3u8Url null")
                            }
                        },
                        onRefresh = {},
                        onItemClick = { slug ->
                            viewModel.loadMovie(slug)
                        },
                        onSelectedServer = {
                            viewModel.selectServer(it)
                        }),//TODO:
                    selectedServer = selectedServer,
                    listRelatedMovie = listRelatedMovie,
                )
            }

            // TopBar chồng lên trên
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
        listRelatedMovie: List<MovieCardUIState> = emptyList(),
        selectedServer: EpisodeUIState? = null,
        callbacks: BodyContentCallbacks,
    ) {
        Column(modifier = modifier) {
            TitleSection(contentUIState, onPlayClick = { callbacks.onPlayClick() })
            Spacer(modifier = Modifier.height(24.dp))
            GenreAndDescription(contentUIState.genres, contentUIState.description)
            Spacer(modifier = Modifier.height(24.dp))
            MoreInfoSection(modifier = Modifier, content = contentUIState)
            CastSection(contentUIState.cast)
            if (listEpisodeUIState.isNotEmpty()) {
                selectedServer?.let {
                    PaginatedEpisodeGridWithPageNumbers(
                        modifier = Modifier,
                        episodeUIStates = it,
                        onEpisodeClick = { item ->
                            callbacks.onEpisodeClick.invoke(item)
                        },
                    )
                }
            }
            MovieTabScreen(
                modifier = Modifier.padding(6.dp),
                listRelatedMovie,
                onItemClick = { item ->
                    callbacks.onItemClick(item.slug)
                })
        }
    }

    @Composable
    fun MoreInfoSection(content: ContentUIState, modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            BaseText("Thể loại: ${content.genres.joinToString(", ")}", style = FidoTheme.typography.subtitle1)
            BaseText("Ngôn ngữ: ${content.lang}", style = FidoTheme.typography.subtitle1)
            BaseText("Trạng thái: ${content.status}", style = FidoTheme.typography.subtitle1)
            BaseText("Chất lượng: ${content.quality}", style = FidoTheme.typography.subtitle1)
        }
    }

    @Composable
    fun TitleSection(content: ContentUIState, onPlayClick: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BaseText(content.title, style = FidoTheme.typography.h3)
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
            BaseText(
                " • ${content.time}",
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
        }
        Spacer(modifier = Modifier.height(13.dp))
        Row {
            BaseButtonWithIcon(
                modifier = Modifier,
                onClick = onPlayClick,
                icon = painterResource(Res.drawable.ic_play_circle),
                text = "Play"
            )
//            Spacer(modifier = Modifier.width(8.dp))
//            BaseOutlinedButton(
//                modifier = Modifier,
//                onClick = { /* TODO: Favorite */ },
//            ) {
//                BaseIcon(src = Res.drawable.ic_heart)
//                BaseText(
//                    "Favorite",
//                    modifier = Modifier.padding(start = 4.dp),
//                    maxLines = 1,
//                    style = FidoTheme.typography.h1
//                )
//            }
        }
    }

    @Composable
    fun GenreAndDescription(genres: List<String>, description: String) {
        BaseText(description, style = FidoTheme.typography.subtitle2)
    }

    @Composable
    fun CastSection(cast: List<CastMemberUIState>) {
        val groupCast = cast.groupBy { it.role }.toList()
        LazyColumn(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp)
        ) {
            itemsIndexed(groupCast) { index, item ->
                val roleText = if (item.first.lowercase() == "director") "Đạo diễn" else "Diễn viên"
                val castMember = item.second.joinToString(", ") { it.name }
                BaseText("${roleText}: $castMember", style = FidoTheme.typography.subtitle1)
            }
        }
    }

    data class BodyContentCallbacks(
        val onItemClick: (String) -> Unit,
        val onRefresh: () -> Unit,
        val onPlayClick: () -> Unit,
        val onSelectedServer: (EpisodeUIState) -> Unit,
        val onEpisodeClick: (EpisodeDetailUIState) -> Unit = {},
    )
}
