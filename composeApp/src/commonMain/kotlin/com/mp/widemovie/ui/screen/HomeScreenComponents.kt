package com.mp.widemovie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.utils.getFilmCategories
import com.mp.widemovie.utils.makeURLRequestImage
import com.mp.widemovie.viewmodel.HomeViewModel
import com.mp.widemovie.viewmodel.TAG
import org.jetbrains.compose.resources.painterResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.ic_play
import widemovie.composeapp.generated.resources.ic_rate
import kotlin.math.round

const val TAG = "HomeScreenComponents"

@Composable
fun Header(modifier: Modifier, viewModel: HomeViewModel, onClicked: (String) -> Unit) {
    val dataResponse = viewModel.stateFlowUI.collectAsState().value
    val movieList = dataResponse?.data?.items ?: emptyList()
    TLog.d(TAG, "Header dataResponse: $dataResponse")
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movieList) { movie ->
            with(movie) {
                val painter =
                    rememberAsyncImagePainter(makeURLRequestImage(thumbUrl.toString()))
                Box {
                    Image(
                        modifier = Modifier.fillParentMaxHeight(0.9f).fillParentMaxWidth(0.33f)
                            .clickable {
                                onClicked(slug.toString())
                            },
                        painter = painter,
                        contentDescription = "image",
                        contentScale = ContentScale.FillBounds,
                    )
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart).padding(start = 10.dp)
                    ) {
                        val rate = tmd?.voteAverage
                        val type = type
                        val year = year
                        Text(
                            name.toString(),
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.fillParentMaxWidth(0.25f),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillParentMaxHeight(0.08f)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_rate),
                                contentDescription = "rate",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillParentMaxHeight(0.06f).aspectRatio(1f)
                            )
                            val rate = round(rate ?: (0.0 * 10 / 10.0))
                            val rateString = if (rate == 0.0) {
                                "7"
                            } else {
                                rate.toString()
                            }
                            BaseText(
                                " $rateString | $type | $year",
                                color = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.align(
                            Alignment.BottomEnd
                        ).padding(end = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_play),
                            contentDescription = "ic_play",
                            contentScale = ContentScale.FillBounds,

                            )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }

        }
    }
}


@Composable
fun Body(modifier: Modifier, viewModel: HomeViewModel, onClicked: (String) -> Unit) {
    val dataResponse = viewModel.stateFlowUI.collectAsState().value
    val movieList = dataResponse?.data?.items?.sortedByDescending { it.tmd?.voteAverage }?.take(15)
        ?: emptyList()
    TLog.d(TAG, "Header movieList: $movieList")
    Column(modifier = modifier.padding(start = 20.dp)) {
        BaseText(
            "Trending Now",
            color = FidoPaletteTokens.PrimaryMain,
            fontSize = FidoTheme.typography.h6.fontSize
        )
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movieList) { movie ->
                with(movie) {
                    val painter =
                        rememberAsyncImagePainter(makeURLRequestImage(thumbUrl.toString()))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.clickable {
                            onClicked(slug.toString())
                        }
                    ) {
                        Image(
                            modifier = Modifier.fillParentMaxHeight(0.9f).fillParentMaxWidth(0.33f),
                            painter = painter,
                            contentDescription = "image",
                            contentScale = ContentScale.FillBounds
                        )
                        val interactionSource = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier.hoverable(
                                enabled = true,
                                interactionSource = interactionSource
                            )
                        ) {
                            Text(
                                name.toString(),
                                color = Color.White,
                                fontSize = 16.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Footer(modifier: Modifier, viewModel: HomeViewModel, onClicked: (String) -> Unit) {
    val dataResponse = viewModel.stateFlowUI.collectAsState().value
    val movieList = dataResponse?.data?.items
        ?: emptyList()
    val filmCategories = getFilmCategories(dataResponse).distinct()
    var selectedCategory by remember {
        mutableStateOf(filmCategories.firstOrNull())
    }
    LaunchedEffect(filmCategories) {
        selectedCategory = filmCategories.firstOrNull()
    }
    TLog.d(TAG, "selectedCategory: $selectedCategory")
    Column(
        modifier = modifier.padding(start = 20.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BaseText(
            "Lastest Shows",
            color = FidoPaletteTokens.PrimaryMain,
            fontSize = FidoTheme.typography.h5.fontSize,
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filmCategories) { category ->
                BaseText(
                    text = category,
                    color = FidoPaletteTokens.Neutral_TextColorsWhite_MidEmp,
                    fontSize = FidoTheme.typography.h6.fontSize,
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
        LazyRow {
            items(movieList.filter { it.category?.firstOrNull()?.name == selectedCategory }) { movie ->
                with(movie) {
                    val painter =
                        rememberAsyncImagePainter(makeURLRequestImage(thumbUrl.toString()))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.clickable {
                            onClicked(slug.toString())
                        }
                    ) {
                        Image(
                            modifier = Modifier.fillParentMaxHeight(0.7f).fillParentMaxWidth(0.3f),
                            painter = painter,
                            contentDescription = "image",
                            contentScale = ContentScale.FillBounds
                        )
                        val interactionSource = remember { MutableInteractionSource() }
                        Box(
                            modifier = Modifier.hoverable(
                                enabled = true,
                                interactionSource = interactionSource
                            )
                        ) {
                            Text(
                                name.toString(),
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier.fillParentMaxWidth(0.25f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

