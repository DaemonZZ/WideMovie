package com.mp.widemovie

import androidx.compose.runtime.Composable
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

enum class UIType {
    Android,
    Web,
    Ios
}