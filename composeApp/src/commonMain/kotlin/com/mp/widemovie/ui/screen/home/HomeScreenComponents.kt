package com.mp.widemovie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.daemonz.base_sdk.model.Item
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.utils.MovieLazyColumn
import com.mp.widemovie.viewmodel.HomeViewModel
import com.mp.widemovie.viewmodel.TAG
import org.jetbrains.compose.resources.stringResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.incomming_movies
import widemovie.composeapp.generated.resources.movies_by_types


const val TAG = "HomeScreenComponents"

@Composable
fun Body(modifier: Modifier, viewModel: HomeViewModel, onClicked: (Item) -> Unit) {
    val dataResponse = viewModel.stateFlowMoviesInComming.collectAsState().value
    val movieList = dataResponse.sortedByDescending { it.tmd?.voteAverage }
    TLog.d(TAG, "Header movieList: $movieList")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier.weight(0.2f),
            verticalArrangement = Arrangement.Bottom
        ) {
            BaseText(
                stringResource(Res.string.incomming_movies),
                color = FidoPaletteTokens.PrimaryMain,
                fontSize = FidoTheme.typography.h2.fontSize,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        MovieLazyColumn(Modifier.weight(1f), 1f, movieList, onClicked)
    }
}


@Composable
fun Footer(modifier: Modifier, viewModel: HomeViewModel, onClicked: (Item) -> Unit) {
    val dataResponse = viewModel.stateFlowCategories.collectAsState().value
    val movieList = viewModel.stateFlowMoviesFooter.collectAsState().value
    val dictCategory = dataResponse?.getFilmCategories()
    val nameCategories = dictCategory?.keys?.toList()
    var selectedCategory by remember {
        mutableStateOf("")
    }
    LaunchedEffect(nameCategories) {
        selectedCategory = nameCategories?.firstOrNull().toString()
    }

    LaunchedEffect(selectedCategory) {
        viewModel.getMoviesByType(dictCategory?.get(selectedCategory).toString())
    }

    TLog.d(TAG, "selectedCategory: $selectedCategory")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp).wrapContentHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BaseText(
                stringResource(Res.string.movies_by_types),
                color = FidoPaletteTokens.PrimaryMain,
                fontSize = FidoTheme.typography.h2.fontSize,
            )
        }

        if (!nameCategories.isNullOrEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.wrapContentHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                items(nameCategories) { category ->
                    BaseText(
                        text = category,
                        color = FidoPaletteTokens.Neutral_TextColorsWhite_MidEmp,
                        fontSize = FidoTheme.typography.body1.fontSize,
                        modifier = Modifier.border(
                            1.dp,
                            FidoPaletteTokens.PrimaryMain,
                            RoundedCornerShape(16.dp)
                        ).clip(RoundedCornerShape(16.dp))
                            .background(if (selectedCategory == category) FidoPaletteTokens.PrimaryMain else Color.Transparent)
                            .padding(12.dp)
                            .clickable {
                                selectedCategory = category
                            })
                }
            }
            MovieLazyColumn(Modifier.weight(0.9f), 1f, movieList, onClicked)
        }
    }
}