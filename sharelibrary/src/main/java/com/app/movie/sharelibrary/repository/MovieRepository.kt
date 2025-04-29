package com.app.movie.sharelibrary.repository

import com.app.movie.sharelibrary.datasource.api.ApiRequestCallBack
import com.app.movie.sharelibrary.datasource.api.Countries
import com.app.movie.sharelibrary.datasource.api.IWebService
import com.app.movie.sharelibrary.datasource.api.TypeList
import com.app.movie.sharelibrary.log.MLog
import com.app.movie.sharelibrary.utils.AppUtils.addOnCompleteListener
import com.app.movie.sharelibrary.utils.AppUtils.addOnFailureListener

class MovieRepository(private val apiService: IWebService) {
    companion object {
        private const val TAG = "MovieRepository"
    }

    suspend fun getHomeData(callback: ApiRequestCallBack) {
        apiService.getHomeData().addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getNewFilms(callback: ApiRequestCallBack) {
        apiService.getNewFilms().addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getSeriesInComing(callback: ApiRequestCallBack) {
        apiService.getSeriesInComing().addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getListAnime(callback: ApiRequestCallBack) {
        apiService.filterData(list = TypeList.Anime.value, country = Countries.NhatBan.value)
            .addOnCompleteListener { res ->
                if (res.data.items.isEmpty()) {
                    MLog.d(TAG, "data is empty")
                    return@addOnCompleteListener
                }
                callback.onSuccess(res.data.items)
            }.addOnFailureListener {
                callback.onFailure(it)
            }
    }

    suspend fun getListMovies(page: String = "", callback: ApiRequestCallBack) {
        apiService.getMovies(page).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getTvShows(page: String, callback: ApiRequestCallBack) {
        apiService.getTvShows(page).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getAllSeries(page: String, callback: ApiRequestCallBack) {
        apiService.getAllSeries(page).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getDataByCategory(category: String, page: String, callback: ApiRequestCallBack) {
        apiService.getCategoryData(category, page).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun loadPlayerData(slug: String, callback: ApiRequestCallBack) {
        apiService.getFilmData(slug).addOnCompleteListener { res ->
            if (res.data.item == null) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.item)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun getRelatedFilms(
        slug: String,
        category: String,
        page: String,
        callback: ApiRequestCallBack
    ) {
        apiService.filterData(
            list = slug,
            category = category,
            page = page
        ).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }


    suspend fun getListFilmVietNam(callback: ApiRequestCallBack) {
        apiService.filterData(
            list = TypeList.Movie.value,
            country = Countries.VietNam.value,
        ).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }

    suspend fun searchFilm(query: String, page: String, callback: ApiRequestCallBack) {
        apiService.search(query, page).addOnCompleteListener { res ->
            if (res.data.items.isEmpty()) {
                MLog.d(TAG, "data is empty")
                return@addOnCompleteListener
            }
            callback.onSuccess(res.data.items)
        }.addOnFailureListener {
            callback.onFailure(it)
        }
    }
}