package com.mp.widemovie.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.buttons.BaseButtonText
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.ui.components.VideoPlayerScreen
import com.mp.widemovie.viewmodel.DetailViewModel
import org.koin.compose.viewmodel.koinViewModel

data class MovieDetail(val slug: String?) : BaseScreen() {
    override val key = "${super.key}?${buildQueryFromParams(mapOf(
        "film" to slug.toString()
    ))}"
    @Composable
    override fun Content() = FidoTheme {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: DetailViewModel = koinViewModel()
        val vid by viewModel.testVideo.collectAsState()
        Column {
            LaunchedEffect (1){
                viewModel.loadMovie(slug.toString())
            }
            BaseButtonText(
                text = "Click Call API",
                onClick = { viewModel.loadMovie(slug.toString()) })
            vid.let {
                TLog.d(TAG, "random: $it")
                VideoPlayerScreen(it.first, it.second)
            }


        }

    }
}
