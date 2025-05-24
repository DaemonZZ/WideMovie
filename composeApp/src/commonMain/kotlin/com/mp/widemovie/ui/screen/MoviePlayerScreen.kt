package com.mp.widemovie.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.viewmodel.DetailViewModel
import org.koin.compose.viewmodel.koinViewModel

data class MoviePlayerScreen(val slug: String?) : BaseScreen() {
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
    }

}