package com.app.movie.sharelibrary.repository

import android.util.Log
import com.app.movie.sharelibrary.BuildConfig
import com.app.movie.sharelibrary.datasource.api.ApiRequestCallBack
import com.app.movie.sharelibrary.datasource.api.IWebService
import com.app.movie.sharelibrary.model.Item
import com.app.movie.sharelibrary.utils.CacheFileEvent
import com.app.movie.sharelibrary.utils.VideoFileMaker
import com.google.gson.GsonBuilder
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieRepositoryIntegrationTestTest {
    private lateinit var repository: MovieRepository
    private val testSource = mutableMapOf<String, String>() //slug - link m3u8

    companion object {
        val EXPECTED_ABNORMAL = listOf(149, 450, 600)
    }

    @BeforeAll
    fun setUpAll() = runBlocking {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()
                )
            )
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(IWebService::class.java)

        repository = MovieRepository(retrofit)

        for (i: Int in 1..10) {
            repository.getAllSeries(i.toString(), object : ApiRequestCallBack {
                override fun onFailure(err: String) {

                }

                override fun onSuccess(listItem: List<Item>) {
                    listItem.forEach { listData ->
                        CoroutineScope(Dispatchers.IO).launch {
                            repository.loadPlayerData(
                                listData.slug,
                                object : ApiRequestCallBack {
                                    override fun onFailure(err: String) {

                                    }

                                    override fun onSuccess(item: Item) {
                                        item.episodes.forEach { ep ->
                                            ep.serverData.forEach { data ->
                                                testSource["${listData.slug}_${data.slug}"] =
                                                    data.m3u8
                                            }
                                        }
                                    }
                                })
                        }


                    }
                    println("onSuccess: ${testSource.size}")
                }

            })
        }

    }

    @TestFactory
    fun `test logic find ad position in video`(): Collection<DynamicTest> =
        testSource.toMap().map {
            println("${it.key} --- ${it.value}")
            DynamicTest.dynamicTest(it.key) {
                val videoFileMaker = VideoFileMaker(object : CacheFileEvent {
                    override fun onCachedFileCreated(file: String, slug: String) {

                    }

                    override fun onCachedError(e: String) {
                        println(e)
                        throw Exception("404 error")
                    }

                    override fun tsCollected(listTs: List<String>, listAbnormal: Map<Int, Int>) {
                        var rs = true
                        listAbnormal.forEach { index ->
                            if (!EXPECTED_ABNORMAL.contains(index.key)) {
                                rs = false
                                println("$index is not expected in ${it.key}")
                            }
                            println("link Ts abnormal: ${listTs[index.value + 1]} index: ${index.value + 1}")
                        }
                        assertTrue(rs)
                    }
                })
                mockkStatic(Log::class)
                every { Log.d(any<String>(), any<String>()) } returns 0
                every { Log.e(any<String>(), any<String>()) } returns 0
                every { Log.i(any<String>(), any<String>()) } returns 0
                videoFileMaker.setM3U8Url(it.value)
                videoFileMaker.setSlug(it.key)
                videoFileMaker.collectTs()
            }
        }

    @AfterEach
    fun tearDown() {
    }
}