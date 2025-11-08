package com.mp.widemovie.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.BannerAdView
import com.mp.widemovie.NativeAdContainer
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.extensions.buildQueryFromParams
import com.mp.widemovie.ui.components.VideoPlayerScreen
import com.mp.widemovie.utils.INVISIBLE
import com.mp.widemovie.utils.VISIBLE
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
        val isVisibleAds = listOf(VISIBLE, INVISIBLE, INVISIBLE).random() == VISIBLE
        var visibleBannerAds by remember {
            mutableStateOf(isVisibleAds)
        }
        TLog.d(TAG, "visibleBannerAds: $visibleBannerAds")
        Box(modifier = Modifier.fillMaxSize()) {
            if (visibleBannerAds) {
                var enableHideAdsButton by remember { mutableStateOf(false) }
                NativeAdContainer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    { enableHideAdsButton = true }
                ) {
                    enableHideAdsButton = true
                }
                BannerAdView(
                    Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(1f)
                )

                val textHideAdsButton =
                    if (enableHideAdsButton) "Hide\nAll Ads" else "Give me\na coffee"
                Button(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = FidoTheme.colorScheme.StatusColorSuccess,
                        disabledBackgroundColor = FidoTheme.colorScheme.StatusColorWarning
                    ),
                    onClick = { visibleBannerAds = !visibleBannerAds },
                    enabled = enableHideAdsButton,
                ) {
                    Text(
                        textHideAdsButton,
                        color = FidoTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }

            } else {
                vid?.let {
                    TLog.d(TAG, "random: $it")
                    VideoPlayerScreen(it.first, it.second)
                }
            }
        }
    }
}