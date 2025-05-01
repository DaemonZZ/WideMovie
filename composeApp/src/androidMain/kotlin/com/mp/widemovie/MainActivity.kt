package com.mp.widemovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.daemonz.base_sdk.repo.AppRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                repo = remember {
                    AppRepository()
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        repo = remember {
            AppRepository()
        }
    )
}