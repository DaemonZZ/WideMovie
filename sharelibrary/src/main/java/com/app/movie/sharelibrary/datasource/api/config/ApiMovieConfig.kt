package com.app.movie.sharelibrary.datasource.api.config

import com.app.movie.sharelibrary.BuildConfig
import com.app.movie.sharelibrary.datasource.api.IWebService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiMovieConfig {
    val retrofit = webService()

    private fun gson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()
    }

    private fun httpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS).addInterceptor(logging).build()
    }

    private fun webService(): IWebService {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    gson()
                )
            )
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient())
            .build()
            .create(IWebService::class.java)
    }
}