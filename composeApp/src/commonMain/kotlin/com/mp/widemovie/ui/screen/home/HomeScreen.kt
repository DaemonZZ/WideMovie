package com.mp.widemovie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.getScreenSize
import com.mp.widemovie.ui.screen.AndroidBottomBar
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.ui.screen.WebNavigatorDrawer
import com.mp.widemovie.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel


class HomeScreen : BaseScreen() {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = koinViewModel()
        val categoryUI = CurrentUIType
        FidoTheme {
            Scaffold(
                bottomBar = { if (categoryUI == UIType.Android) AndroidBottomBar(0) },
                drawerBackgroundColor = Color.Transparent,
                drawerElevation = 0.dp,
                drawerContent = { if (categoryUI == UIType.Web) WebNavigatorDrawer() },
                drawerGesturesEnabled = (categoryUI == UIType.Web),
            ) {
                TLog.d("ScreenSize", getScreenSize().toString())
                Column(
                    Modifier.fillMaxWidth().background(FidoPaletteTokens.Secondary10),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Body(Modifier.fillMaxWidth().weight(1f), viewModel) {
                        nav += MovieDetail(it)
                    }
                    Footer(Modifier.fillMaxWidth().weight(1.5f), viewModel) {
                        nav += MovieDetail(it)
                    }
                }
            }
        }
    }
}