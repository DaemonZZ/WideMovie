package com.mp.widemovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.mp.widemovie.ui.screen.HomeScreen
import com.mp.widemovie.viewmodel.HomeViewModel
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by inject(HomeViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(HomeScreen())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}