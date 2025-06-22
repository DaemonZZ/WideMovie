package com.mp.widemovie.ui.screen.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mp.widemovie.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.debounce

const val DELAY_TIME_SEARCH = 3000L

@Composable
fun SearchView(viewModel: SearchViewModel) {
    var inputSearchState by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        textStyle = TextStyle(color = Color.White),
        modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
        value = inputSearchState,
        onValueChange = {
            inputSearchState = it
        },
        label = {
            Text("Search Movies")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, // Generic text input
            imeAction = ImeAction.Done
        ),

        )
    LaunchedEffect(inputSearchState) {
        snapshotFlow { inputSearchState }
            // Deplay time for spam request, each 3s get last value if no any value emitted
            .debounce(DELAY_TIME_SEARCH)
            .collect {
                if (it.trim().isNotEmpty()) {
                    viewModel.searchMoviesByName(it)
                }
            }
    }
}



