package com.mp.widemovie.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.getScreenSize
import com.mp.widemovie.ui.screen.AndroidBottomBar
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.ui.screen.WebNavigatorDrawer
import com.mp.widemovie.utils.makeURLRequestImage
import com.mp.widemovie.viewmodel.SearchViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.img_place_holder

class SearchMovie : BaseScreen() {
    @Composable
    override fun Content() {
        val viewModel: SearchViewModel = koinViewModel()
        val nav = LocalNavigator.currentOrThrow
        FidoTheme {
            TLog.d("ScreenSize", getScreenSize().toString())
            val movieLists =
                viewModel.uiState.collectAsState().value.listMovie?.data?.items ?: emptyList()
            val categoryUI = CurrentUIType
            Scaffold(
                bottomBar = { if (categoryUI == UIType.Android) AndroidBottomBar(1) },
                drawerBackgroundColor = Color.Transparent,
                drawerElevation = 0.dp,
                drawerContent = { if (categoryUI == UIType.Web) WebNavigatorDrawer() },
                drawerGesturesEnabled = (categoryUI == UIType.Web),
            ) {
                Column {
                    SearchView(viewModel)
                    Spacer(Modifier.fillMaxHeight(0.02f))
                    if (true) {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(movieLists.sortedByDescending { it.year }) { item ->
                                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f)) {
                                    BeautifulMovieListItem(
                                        item,
                                        Modifier.fillMaxWidth().fillMaxHeight(0.2f)
                                    ) {
                                        nav += MovieDetail(item.slug.toString())
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
    }

    @Composable
    fun BeautifulMovieListItem(
        movie: Item, // Pass your movie data object
        modifier: Modifier = Modifier,
        onClicked: () -> Unit
    ) {
        val painter =
            rememberAsyncImagePainter(makeURLRequestImage(movie.posterUrl.toString())) {
                painterResource(Res.drawable.img_place_holder)
            }
        with(movie) {
            Card(
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable{
                        onClicked()
                    }, // Padding around the card
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp), // Padding inside the card
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Movie Poster Image
                    Image(
                        painter = painter,
                        contentDescription = "$name poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(0.3f)
                            .aspectRatio(2f / 3f) // Common movie poster aspect ratio (e.g., width 2, height 3)
                            .clip(RoundedCornerShape(8.dp))
                            .background(FidoTheme.colorScheme.onBackground.copy(alpha = 0.1f)) // Placeholder bg
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Movie Details Column
                    Column(
                        modifier = Modifier.weight(0.7f), // Take up 70% of the Row's width
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = name,
                            style = FidoTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            color = FidoTheme.colorScheme.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Year: ${movie.year}",
                            style = FidoTheme.typography.body1,
                            color = FidoTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Type: $type",
                            style = FidoTheme.typography.body1,
                            color = FidoTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        val countryName = country.firstOrNull()?.name ?: "Unknown"
                        Text(
                            text = "Country: $countryName",
                            style = FidoTheme.typography.body1,
                            color = FidoTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Quality: $quality",
                            style = FidoTheme.typography.body1,
                            color = FidoTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )

                        // You can add more details here, like genre, short description etc.
                    }
                }
            }
        }

    }

}