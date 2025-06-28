package com.mp.widemovie.ui.mapping

import com.daemonz.base_sdk.IMG_BASE_URL
import com.daemonz.base_sdk.model.Episode
import com.daemonz.base_sdk.model.Item
import com.mp.widemovie.ui.uistate.CastMemberUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState
import com.mp.widemovie.ui.uistate.EpisodeUIState
import com.mp.widemovie.ui.uistate.MovieCardUIState

fun Episode.toEpisodeUIState(): EpisodeUIState {
    return EpisodeUIState(
        serverName = serverName,
        serverData = serverData.filter { it.m3u8.isNotEmpty() }.map {
            EpisodeDetailUIState(
                slug = it.slug,
                title = it.name,
                thumbnailUrl = it.filename,
                m3u8Url = it.m3u8,
                url = it.url
            )
        })
}

fun Item.toContentUIState(): ContentUIState {
    val listCast = director.map {
        CastMemberUIState(
            name = it,
            role = "Director",
        )
    } + actor.map {
        CastMemberUIState(
            name = it,
            role = "Actor",
        )
    }
    val listGenres = category.map { it.name }
    return ContentUIState(
        title = name,
        rating = imdb?.voteAverage ?: 0.0f,
        releaseYear = year.toInt(),
        ageRating = "13+",
        country = country.firstOrNull()?.name ?: "Unknow",
        hasSubtitle = true,
        isPlayable = true,
        isDownloadable = true,
        genres = listGenres,
        description = content.replace(Regex("<.*?>"), ""),
        cast = listCast,
        posterUrl = "${IMG_BASE_URL}/uploads/movies/$posterUrl",
        time = time,
        lang = language,
        status = episodeCurrent,
        quality = quality,
        trailerUrl = trailerUrl,
    )
}

fun Item.toMovieCardUIState(): MovieCardUIState {
    return MovieCardUIState(
        title = name,
        posterUrl = "${IMG_BASE_URL}/uploads/movies/$posterUrl",
        time = time,
        slug = slug,
        status = episodeCurrent,
    )
}