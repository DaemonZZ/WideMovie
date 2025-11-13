package com.mp.widemovie

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daemonz.base_sdk.model.SelectedFilmInfo
import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

expect val CurrentUIType: UIType

expect fun saveSelectedMovie(selectedFilm: SelectedFilmInfo)

expect suspend fun getAllWatchedMovies(): List<SelectedFilmInfo>

@Composable
expect fun getScreenSize(): Pair<Int, Int>

@Composable
expect fun InitializeAds()

@Composable
expect fun BannerAdView(modifier: Modifier)

@Composable
expect fun NativeAdContainer(modifier: Modifier = Modifier, onFailed: () -> Unit, callBackEndAds: () -> Unit)
enum class UIType {
    Android,
    Web,
    Ios
}