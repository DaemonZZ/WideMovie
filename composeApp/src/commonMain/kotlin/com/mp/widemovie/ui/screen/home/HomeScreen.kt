package com.mp.widemovie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.saveSelectedMovie
import com.mp.widemovie.ui.screen.AndroidBottomBar
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.ui.screen.WebNavigatorDrawer
import com.mp.widemovie.ui.screen.home.UiType.currentType
import com.mp.widemovie.ui.screen.search.LazyGridFilms
import com.mp.widemovie.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.incomming_movies

object UiType {
    val currentType = CurrentUIType
}

class HomeScreen : BaseScreen() {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = koinViewModel()
        FidoTheme {
            Scaffold(
                bottomBar = { if (currentType == UIType.Android) AndroidBottomBar(0) },
                drawerBackgroundColor = Color.Transparent,
                drawerElevation = 0.dp,
                drawerContent = { if (currentType == UIType.Web) WebNavigatorDrawer() },
                drawerGesturesEnabled = (currentType == UIType.Web),
            ) { innerPadding ->
                val homeContent = HOME_CONTENT.values().toList()
                TLog.d("HomeContent", "$homeContent")
                LazyColumn(
                    Modifier.fillMaxSize().background(FidoPaletteTokens.Secondary10)
                        .padding(innerPadding).padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(homeContent) { content ->
                        val ratioContent = content.ratio
                        when (content) {
                            HOME_CONTENT.INCOMMING_MOVIES -> {
                                val dataResponse =
                                    viewModel.stateFlowMoviesInComing.collectAsState().value
                                val header = stringResource(Res.string.incomming_movies)
                                Body(
                                    Modifier.fillMaxWidth().fillParentMaxHeight(ratioContent),
                                    header,
                                    dataResponse
                                ) { movie ->
                                    nav += MovieDetail(movie.slug.toString())
                                    saveSelectedMovie(movie.convertToRecentSearch())
                                }
                            }

                            HOME_CONTENT.MOVIE_BY_TYPE -> {
                                Footer(
                                    Modifier.fillMaxWidth().fillParentMaxHeight(ratioContent),
                                    viewModel
                                ) { movie ->
                                    nav += MovieDetail(movie.slug.toString())
                                    saveSelectedMovie(movie.convertToRecentSearch())
                                }
                            }

                            HOME_CONTENT.TV_SHOWS -> {
                                val dataResponse = viewModel.stateFlowTvShows.collectAsState().value
                                val header = "Tv Shows"
                                Body(
                                    Modifier.fillMaxWidth().fillParentMaxHeight(ratioContent),
                                    header,
                                    dataResponse
                                ) { movie ->
                                    nav += MovieDetail(movie.slug.toString())
                                    saveSelectedMovie(movie.convertToRecentSearch())
                                }
                            }

                            HOME_CONTENT.PHIM_BO -> {
                                val dataResponse = viewModel.stateFlowPhimBo.collectAsState().value
                                val header = "Phim Bo"
                                Body(
                                    Modifier.fillMaxWidth().fillParentMaxHeight(ratioContent),
                                    header,
                                    dataResponse
                                ) { movie ->
                                    nav += MovieDetail(movie.slug.toString())
                                    saveSelectedMovie(movie.convertToRecentSearch())
                                }
                            }

                            HOME_CONTENT.PHIM_LE -> {
                                val dataResponse = viewModel.stateFlowFilmLe.collectAsState().value
                                val header = "Phim Le"
                                Column {
                                    BaseText(
                                        header,
                                        color = FidoPaletteTokens.PrimaryMain,
                                        fontSize = FidoTheme.typography.h2.fontSize,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    LazyGridFilms(
                                        dataResponse,
                                        nav,
                                        Modifier.fillMaxWidth().fillParentMaxHeight(ratioContent),
                                        contentPadding = 0
                                    )
                                }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    enum class HOME_CONTENT(val ratio: Float) {
        // ORDER OF HOME CONTENT
        MOVIE_BY_TYPE(0.5f),
        TV_SHOWS(0.5f),
        PHIM_BO(0.5f),
        PHIM_LE(0.5f),
        INCOMMING_MOVIES(0.3f),
    }

}