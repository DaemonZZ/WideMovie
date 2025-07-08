package com.mp.widemovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.auth.FirebaseAuth
import com.mp.widemovie.ui.screen.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(HomeScreen())
            val auth = FirebaseAuth.getInstance()
            auth.signInAnonymously()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}