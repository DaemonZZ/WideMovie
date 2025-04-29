package com.app.movie.sharelibrary.repository

import com.app.movie.sharelibrary.datasource.api.IWebService
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class MovieRepositoryTest {

    private lateinit var api: IWebService
    private lateinit var repo: MovieRepository

    @BeforeEach
    fun setUp() {
        api = mockk()
        repo = MovieRepository(api)
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun test1() = runTest {
        assert(true)
    }
}

