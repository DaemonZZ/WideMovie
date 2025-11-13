package com.mp.widemovie.utils

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.daemonz.base_sdk.utils.TLog
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.mp.widemovie.BuildConfig

@Composable
fun BannerAdViewAndroid(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.FULL_BANNER)
                adUnitId = BuildConfig.ADS_FIXED_SIZE_BANNER_ID // ID thử nghiệm cho Banner
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}


fun loadNativeAd(context: Context, onFailed: () -> Unit, onAdLoaded: (NativeAd) -> Unit) {
    val adLoader = AdLoader.Builder(context, BuildConfig.ADS_NATIVE_VIDEO_ID) // ID Native Video
        .forNativeAd { nativeAd ->
            onAdLoaded(nativeAd)
            TLog.e("loadNativeAd", "Ad load success")

        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                onFailed()
                Log.e("loadNativeAd", "Ad failed to load: ${adError.message}")
            }
        })
        .withNativeAdOptions(
            NativeAdOptions.Builder()
                .setVideoOptions(VideoOptions.Builder().setStartMuted(true).build())
                .build()
        )
        .build()

    adLoader.loadAd(AdRequest.Builder().build())
}

@Composable
fun NativeAdViewInCompose(nativeAd: NativeAd, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .then(modifier),
        factory = {
            val headlineView = TextView(context).apply {
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.Black.toArgb())
                // Quan trọng: Cần có layout params để đặt vị trí
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            val bodyView = TextView(context).apply {
                textSize = 12f
                setTextColor(Color.Gray.toArgb())
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            val mediaView = MediaView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Chiếm toàn bộ chiều rộng
                    ViewGroup.LayoutParams.MATCH_PARENT - bodyView.height - headlineView.height // ~1.77 (16:9), ví dụ
                )
            }

            val adView = NativeAdView(context).apply {
                addView(headlineView)
                addView(bodyView)
                addView(mediaView)
                this.headlineView = headlineView
                this.bodyView = bodyView
                this.mediaView = mediaView
            }

            (adView.headlineView as TextView).text = ""
            (adView.bodyView as TextView).text = ""
            adView.setNativeAd(nativeAd)

            adView // NativeAdView
        }
    )
}

@Composable
fun NativeAdContainerAndroid(
    modifier: Modifier = Modifier,
    onFailed: () -> Unit,
    callBackEndAds: () -> Unit
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var videoController by remember { mutableStateOf<VideoController?>(null) }
    LaunchedEffect(Unit) {
        loadNativeAd(context, onFailed) { ad ->
            nativeAd = ad
            if (ad.mediaContent?.hasVideoContent() == true) {
                videoController = ad.mediaContent?.videoController
            }
        }
    }

    LaunchedEffect(videoController) {
        val controller = videoController ?: return@LaunchedEffect

        val videoCallback = object : VideoController.VideoLifecycleCallbacks() {
            override fun onVideoEnd() {
                super.onVideoEnd()
                callBackEndAds()
            }
        }

        controller.videoLifecycleCallbacks = videoCallback


    }

    if (nativeAd != null) {
        NativeAdViewInCompose(nativeAd!!, modifier)
    } else {
        // Placeholder
    }
}