package com.mp.widemovie.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.ui.components.VideoPlayerScreen
import com.mp.widemovie.viewmodel.MoviePlayerViewModel
import org.koin.compose.viewmodel.koinViewModel

data class MoviePlayerScreen(val slug: String?, val url: String?) : BaseScreen() {
    override val key = "${super.key}?${
        buildQueryFromParams(
            mapOf(
                "film" to slug.toString(),
                "url" to url.toString(),
            )
        )
    }"

    @Composable
    override fun Content() = FidoTheme(isDarkTheme = true) {
        val viewModel: MoviePlayerViewModel = koinViewModel()
        val vid by viewModel.testVideo.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.prepareVideo(url = url!!, slug = slug!!)
        }

        vid?.let {
            TLog.d(TAG, "random: $it")
            VideoPlayerScreen(it.first, it.second)
        }
    }
}