package com.mp.widemovie.ui

import com.mp.widemovie.ui.screen.Movie
import com.mp.widemovie.ui.uistate.CastMemberUIState
import com.mp.widemovie.ui.uistate.ContentUIState
import com.mp.widemovie.ui.uistate.EpisodeDetailUIState

object DemoData {

    //Only For test
    val dummyEpisodeDetailUIStates = listOf(
        EpisodeDetailUIState("1", "Episode - 1", "https://example.com/thumb1.jpg"),
        EpisodeDetailUIState("2", "Episode - 2", "https://example.com/thumb2.jpg"),
        EpisodeDetailUIState("3", "Episode - 3", "https://example.com/thumb3.jpg")
    )

    //Only For test
    val sampleContent = ContentUIState(
        title = "The Amazing Spider Man",
        rating = 9.8f,
        releaseYear = 2022,
        ageRating = "13+",
        country = "United States",
        hasSubtitle = true,
        isPlayable = true,
        isDownloadable = true,
        genres = listOf("Animation", "Cartoon"),
        description = "Lörem ipsum suben beng häkönat pärlifera...",
        cast = List(6) {
            CastMemberUIState(
                name = "James",
                role = "Director",
                imageUrl = null
            )
        }
    )

    val sampleMovies = listOf(
        Movie("Pushpa", "pushpa.jpg", "8.5"),
        Movie("Oblivion", "oblivion.jpg", "8.5"),
        Movie("Fallout", "fallout.jpg", "8.5"),
        Movie("Top Gun Maverick", "topgun.jpg", "8.5"),
        Movie("Pushpa", "pushpa.jpg", "8.5"),
        Movie("Oblivion", "oblivion.jpg", "8.5"),
        Movie("Fallout", "fallout.jpg", "8.5"),
        Movie("Top Gun Maverick", "topgun.jpg", "8.5"),
    )

}