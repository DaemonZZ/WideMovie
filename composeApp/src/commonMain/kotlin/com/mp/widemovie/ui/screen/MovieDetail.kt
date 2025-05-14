package com.mp.widemovie.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.ui.components.Banner
import com.mp.widemovie.ui.components.Title
import com.mp.widemovie.ui.components.TitleAare
import com.mp.widemovie.viewmodel.DetailViewModel
import org.koin.compose.viewmodel.koinViewModel

data class MovieDetail(val slug: String?) : BaseScreen() {
    override val key = "${super.key}?${
        buildQueryFromParams(
            mapOf(
                "film" to slug.toString()
            )
        )
    }"

    @Composable
    override fun Content() = FidoTheme {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: DetailViewModel = koinViewModel()
        val vid by viewModel.testVideo.collectAsState()
        Column {
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

            Box(modifier = Modifier.fillMaxWidth()) {
                Banner(modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f))
                TitleAare(
                    onBackClick = { nav.popUntilRoot() },
                    {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column {
                Title({}, {}, modifier = Modifier.fillMaxWidth())
            }
        }

    }
}
