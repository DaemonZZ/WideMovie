package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.daemonz.common.components.buttons.BaseButtonWithIcon
import com.daemonz.common.components.buttons.BaseIcon
import com.daemonz.common.components.buttons.BaseIconButton
import com.daemonz.common.components.buttons.BaseOutlinedButton
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.ui.components.BaseTopAppBar
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
        val vid by viewModel.testVideo.collectAsState()
//            LaunchedEffect (1){
//                viewModel.loadMovie(slug.toString())
//            }
//            BaseButtonText(
//                text = "Click Call API",
//                onClick = { viewModel.loadMovie(slug.toString()) })
//            vid.let {
//                TLog.d(TAG, "random: $it")
//                VideoPlayerScreen(it.first, it.second)
//            }
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
                Image(
                    modifier = Modifier
                        .height(maxImageSize)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .graphicsLayer {
//                            scaleY = imageScale
//                            // Center the image vertically as it scales
                            translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                        },
                    painter = painterResource(Res.drawable.banner_ex),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                )
                BodyContent(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .offset {
                            IntOffset(0, currentImageSize.roundToPx())
                        },
                    contentState = sampleContent
                )
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
        contentState: ContentState = ContentState(),
    ) {
        Column(modifier = modifier) {
            TitleSection(contentState)
            Spacer(modifier = Modifier.height(24.dp))
            GenreAndDescription(contentState.genres, contentState.description)
            Spacer(modifier = Modifier.height(24.dp))
            CastSection(contentState.cast)
            EpisodesSection(modifier = Modifier, episodes = dummyEpisodes, onEpisodeClick = {})
            MovieTabScreen(modifier = Modifier.padding(6.dp))
        }
    }

    @Composable
    fun TitleSection(content: ContentState) {
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
                onClick = { /* TODO: Play */ },
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
    fun CastSection(cast: List<CastMember>) {
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(member.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
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
    fun EpisodesSection(
        modifier: Modifier = Modifier,
        episodes: List<Episode>,
        onEpisodeClick: (Episode) -> Unit,
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
                itemsIndexed(episodes) { indexKey, episode ->
                    EpisodeCard(episode = episode, onClick = onEpisodeClick)
                }
            }
        }
    }

    @Composable
    fun EpisodeCard(episode: Episode, onClick: (Episode) -> Unit) {
        Card(
            modifier = Modifier
                .width(140.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = FidoTheme.shapes.medium,
        ) {
            Image(
                painter = rememberAsyncImagePainter(episode.thumbnailUrl),
                contentDescription = episode.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Play Icon Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                BaseIcon(
                    src = Res.drawable.ic_play_circle,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp).align(Alignment.Center)
                )
                BaseText(
                    text = episode.title,
                    style = FidoTheme.typography.caption,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    data class Episode(
        val id: Int,
        val title: String,
        val thumbnailUrl: String,
    )

    data class CastMember(
        val name: String,
        val role: String,
        val imageUrl: String, // or you can use painter/resource ID if from local
    )

    data class ContentState(
        val title: String = "",
        val rating: Double = 0.0,
        val releaseYear: Int = 0,
        val ageRating: String = "",
        val country: String = "",
        val hasSubtitle: Boolean = false,
        val isBookmarked: Boolean = false,
        val isShared: Boolean = false,
        val isPlayable: Boolean = true,
        val isDownloadable: Boolean = true,
        val genres: List<String> = emptyList(),
        val description: String = "",
        val cast: List<CastMember> = emptyList(),
    )

    //Only For test
    private val dummyEpisodes = listOf(
        Episode(1, "Episode - 1", "https://example.com/thumb1.jpg"),
        Episode(2, "Episode - 2", "https://example.com/thumb2.jpg"),
        Episode(3, "Episode - 3", "https://example.com/thumb3.jpg")
    )

    //Only For test
    private val sampleContent = ContentState(
        title = "The Amazing Spider Man",
        rating = 9.8,
        releaseYear = 2022,
        ageRating = "13+",
        country = "United States",
        hasSubtitle = true,
        isPlayable = true,
        isDownloadable = true,
        genres = listOf("Animation", "Cartoon"),
        description = "Lörem ipsum suben beng häkönat pärlifera...",
        cast = List(6) {
            CastMember(
                name = "James",
                role = "Director",
                imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
            )
        }
    )

}
