package com.mp.widemovie

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.daemonz.base_sdk.repo.AppRepository

fun MainViewController() = ComposeUIViewController { App(
    repo = remember {
        AppRepository()
    }
) }