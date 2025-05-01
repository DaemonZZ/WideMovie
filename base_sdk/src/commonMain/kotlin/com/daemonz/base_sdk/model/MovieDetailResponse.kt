package com.daemonz.base_sdk.model

data class MovieDetailResponse(
    val status: Boolean,
    val msg: String,
    val movie: Movie,
    val episodes: List<Episode>,
)

data class Movie(
    val tmdb: Tmdb?,
    val imdb: Imdb?,
    val created: TimeWrapper,
    val modified: TimeWrapper,
    val _id: String,
    val name: String,
    val slug: String,
    val origin_name: String,
    val content: String,
    val type: String,
    val status: String,
    val thumb_url: String,
    val poster_url: String,
    val is_copyright: Boolean,
    val sub_docquyen: Boolean,
    val chieurap: Boolean,
    val trailer_url: String,
    val time: String,
    val episode_current: String,
    val episode_total: String,
    val quality: String,
    val lang: String,
    val notify: String,
    val showtimes: String,
    val year: Int,
    val view: Int,
    val actor: List<String>,
    val director: List<String>,
    val category: List<Category>,
    val country: List<Country>,
)

data class Tmdb(
    val type: String,
    val id: String,
    val season: String?, // hoặc Int? nếu backend trả về dạng số
    val vote_average: Double,
    val vote_count: Int,
)

data class Imdb(
    val id: String,
)

data class TimeWrapper(
    val time: String, // Hoặc dùng Instant nếu cần
)


data class ServerData(
    val name: String,
    val slug: String,
    val filename: String,
    val link_embed: String,
    val link_m3u8: String,
)

