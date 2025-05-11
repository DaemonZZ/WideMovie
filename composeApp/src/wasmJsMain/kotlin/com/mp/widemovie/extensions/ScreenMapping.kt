package com.mp.widemovie.extensions

import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.base.getScreenKey
import com.mp.widemovie.ui.screen.HomeScreen
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.ui.screen.TestScreen
import kotlinx.browser.window

object ScreenMapping {
    private const val TAG = "ScreenMapping"
    fun getScreen(path: String): BaseScreen =
        if(window.location.search.contains("?"))
            getScreenWithParam(path)
    else getScreenWithoutParam(path)

    /***
     * Screen nào có param thì quăng vào đây
     */
    private fun getScreenWithParam(path: String): BaseScreen {
        TLog.d(TAG, "getScreenWithParam: $path")
        return when{
            path.startsWith(getScreenKey(MovieDetail::class)) -> {
                val id = getQueryParameter( "film")
                MovieDetail(id)
            }
            path.startsWith(getScreenKey(TestScreen::class)) -> {
                val id = getQueryParameter( "param")
                TestScreen(id)
            }
            else -> HomeScreen() // fallback nếu route không hợp lệ
        }
    }

    /***
     * Screen nào không có param thì quăng vào đây
     */
    private fun getScreenWithoutParam(path: String): BaseScreen {
        TLog.d(TAG, "getScreenWithoutParam: $path")
        return when(path) {
            getScreenKey(HomeScreen::class) -> HomeScreen()
            else -> HomeScreen()// fallback nếu route không hợp lệ
        }
    }

    private fun getQueryParameter(key: String): String? {
        val query = window.location.search.removePrefix("?")
        val params = query.split("&")
        return params
            .map { it.split("=") }
            .firstOrNull { it.size == 2 && it[0] == key }
            ?.get(1)
    }

}