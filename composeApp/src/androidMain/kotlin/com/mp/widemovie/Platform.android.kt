package com.mp.widemovie

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.daemonz.base_sdk.model.SelectedFilmInfo
import com.daemonz.base_sdk.utils.TLog
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.scope.Scope
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())

actual val CurrentUIType: UIType = UIType.Android

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun getScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return Pair(screenWith, screenHeight)
}

actual fun saveSelectedMovie(selectedFilm: SelectedFilmInfo) {
    val TAG = "saveSelectedMovie"
    val db = Firebase.firestore
    TLog.d(TAG, "object: $selectedFilm")
    // TODO need to save map by account
    with(selectedFilm) {
        db.collection("watched_movies").document(id)
            .set(selectedFilm)
            .addOnSuccessListener { TLog.d(TAG, "Successfully written!") }
            .addOnFailureListener { e -> TLog.d(TAG, "Error writing document: $e") }
    }
}


actual suspend fun getAllWatchedMovies(): List<SelectedFilmInfo> {
    val TAG = "getAllWatchedMovies"
    val db = Firebase.firestore
    val collectionRef = db.collection("watched_movies")
    val result = arrayListOf<SelectedFilmInfo>()
    return suspendCancellableCoroutine<List<SelectedFilmInfo>> { continuation ->
        CoroutineScope(Dispatchers.IO).async {
            collectionRef.get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val docData = document.data
                    val jsonString = Gson().toJson(docData)
                    val selectedFilmInfo = jsonString.jsonToObject(SelectedFilmInfo::class.java)
                    selectedFilmInfo?.let {
                        if(it !in result){
                            result.add(it)
                        }
                    }
                    TLog.d(TAG, "${selectedFilmInfo?.id}: $selectedFilmInfo")
                    // Just only get lastest five movies wathed
                }
                if (continuation.isActive) {
                    continuation.resume(result)
                }
            }
                .addOnFailureListener { exception ->
                    TLog.d(TAG, "Error getting documents: $exception")
                    if (continuation.isActive) {
                        continuation.resumeWithException(exception)
                    }
                }
        }
    }
}

fun <T> String.jsonToObject(classOfT: Class<T>, gson: Gson = Gson()): T? {
    return try {
        gson.fromJson(this, classOfT)
    } catch (e: JsonSyntaxException) {
        // Handle specific Gson parsing errors
        println("Error parsing JSON to ${classOfT.simpleName} with Gson: ${e.message}")
        null
    } catch (e: Exception) {
        // Handle other potential errors
        println("An unexpected error occurred parsing JSON to ${classOfT.simpleName} with Gson: ${e.message}")
        null
    }
}