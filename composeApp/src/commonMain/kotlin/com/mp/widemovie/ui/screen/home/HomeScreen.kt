package com.mp.widemovie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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
import com.mp.widemovie.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

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
                Column(
                    Modifier.fillMaxWidth().background(FidoPaletteTokens.Secondary10)
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Body(Modifier.fillMaxWidth().weight(1f), viewModel) { movie ->
                        nav += MovieDetail(movie.slug.toString())
                        saveSelectedMovie(movie.convertToRecentSearch())
                    }
                    Footer(Modifier.fillMaxWidth().weight(1.5f), viewModel) { movie ->
                        nav += MovieDetail(movie.slug.toString())
                        saveSelectedMovie(movie.convertToRecentSearch())
                    }
                }
            }
        }
    }

}