package com.app.movie.sharelibrary.datasource.api

import com.app.movie.sharelibrary.model.ListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IWebService {
    @GET("home")
    suspend fun getHomeData(): Response<ListData>

    @GET("phim/{slug}")
    suspend fun getFilmData(
        @Path("slug") slug: String
    ): Response<ListData>

    @GET("danh-sach/{list}")
    suspend fun filterData(
        @Path("list") list: String,
        @Query("sort_field") sortBy: String = SortField.LastUpdated.value,
        @Query("category") category: String = Category.All.value,
        @Query("country") country: String = "",
        @Query("year") year: String = "",
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/phim-moi")
    suspend fun getNewFilms(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/phim-bo-dang-chieu")
    suspend fun getSeriesInComing(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/hoat-hinh")
    suspend fun getListAnime(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/phim-le")
    suspend fun getMovies(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/tv-shows")
    suspend fun getTvShows(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("danh-sach/phim-bo")
    suspend fun getAllSeries(
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("tim-kiem")
    suspend fun search(
        @Query("keyword") keyword: String,
        @Query("page") page: String = ""
    ): Response<ListData>

    @GET("the-loai/{slug}")
    suspend fun getCategoryData(
        @Path("slug") slug: String,
        @Query("page") page: String = ""
    ): Response<ListData>
}