package com.mp.widemovie.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.getAllWatchedMovies
import com.mp.widemovie.getScreenSize
import com.mp.widemovie.ui.screen.AndroidBottomBar
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.ui.screen.WebNavigatorDrawer
import com.mp.widemovie.utils.MovieLazyColumn
import com.mp.widemovie.utils.WindowType
import com.mp.widemovie.utils.getWindowType
import com.mp.widemovie.viewmodel.SearchViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.ic_tv

class SearchMovie : BaseScreen() {
    @Composable
    override fun Content() {
        val viewModel: SearchViewModel = koinViewModel()
        val nav = LocalNavigator.currentOrThrow
        FidoTheme {
            TLog.d("ScreenSize", getScreenSize().toString())
            val movieLists =
                viewModel.uiState.collectAsState().value.listMovie?.data?.items ?: emptyList()
            val cartoon =
                viewModel.cartoonState.collectAsState().value?.data?.items ?: emptyList()
            val categoryUI = CurrentUIType
            Scaffold(
                bottomBar = { if (categoryUI == UIType.Android) AndroidBottomBar(1) },
                drawerBackgroundColor = Color.Transparent,
                drawerElevation = 0.dp,
                drawerContent = { if (categoryUI == UIType.Web) WebNavigatorDrawer() },
                drawerGesturesEnabled = (categoryUI == UIType.Web),
            ) { innerPadding ->
                var watchedMovies by remember {
                    mutableStateOf(listOf<Item>())
                }
                val windowType = getWindowType()
                // TODO structure screen 2 row, one column
                val widthSize = when (windowType) {
                    WindowType.COMPACT -> 1f
                    WindowType.MEDIUM -> 1f
                    WindowType.EXPANDED -> 1f
                }
                Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_tv),
                        contentDescription = "Icon television",
                        modifier = Modifier.fillMaxWidth(),
                        tint = Color.Red
                    )
                    SearchView(viewModel)
                    if (movieLists.isEmpty()){
                        BaseText(
                            "CHOSE BY EVERYONE & YOU",
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            fontSize = FidoTheme.typography.h2.fontSize,
                            color = FidoPaletteTokens.PrimaryMain
                        )
                        MovieLazyColumn(
                            Modifier.fillMaxHeight(0.3f).fillMaxWidth(widthSize),
                            1f,
                            watchedMovies
                        ) { nav += MovieDetail(it.slug.toString()) }
                        BaseText(
                            "Cartoon",
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            fontSize = FidoTheme.typography.h2.fontSize,
                            color = FidoPaletteTokens.PrimaryMain
                        )
                        LazyGridFilms(cartoon, nav, Modifier.fillMaxHeight())
                        LaunchedEffect(true) {
                            viewModel.getCartoon()
                            watchedMovies = getAllWatchedMovies().map { it.convertToItem() }
                        }
                    }else{
                        LazyGridFilms(movieLists, nav)
                    }
                }
            }
        }
    }
}