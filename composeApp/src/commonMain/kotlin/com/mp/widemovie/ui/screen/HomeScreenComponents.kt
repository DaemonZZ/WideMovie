package com.mp.widemovie.ui.screen

import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.daemonz.base_sdk.utils.TLog
import com.daemonz.common.components.text.BaseText
import com.daemonz.common.theme.FidoPaletteTokens
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.getScreenSize
import com.mp.widemovie.utils.makeURLRequestImage
import com.mp.widemovie.viewmodel.HomeViewModel
import com.mp.widemovie.viewmodel.TAG
import com.seiko.imageloader.model.ImageRequest
import io.ktor.http.headers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.incomming_movies
import widemovie.composeapp.generated.resources.movies_by_types
import kotlin.math.roundToInt


const val TAG = "HomeScreenComponents"

@Composable
fun Body(modifier: Modifier, viewModel: HomeViewModel, onClicked: (String) -> Unit) {
    val dataResponse = viewModel.stateFlowMoviesInComming.collectAsState().value
    val movieList = dataResponse.sortedByDescending { it.tmd?.voteAverage }
    TLog.d(TAG, "Header movieList: $movieList")
    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp),
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
            )
        }

        val lazyState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val customFlingBehavior = rememberSnappingFlingBehavior(lazyState, coroutineScope)
        LazyRow(
            modifier = Modifier.weight(0.8f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            flingBehavior = customFlingBehavior,
            state = lazyState
        ) {
            items(movieList) { movie ->
                with(movie) {
                    val painter = rememberAsyncImagePainter(makeURLRequestImage(posterUrl.toString()))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight().clickable {
                            onClicked(slug.toString())
                        }
                    ) {
                        val ratio = 1 / 2.5
                        val height = getScreenSize().second * ratio * 0.6
                        val width = height * 3 / 4
                        Image(
                            modifier = Modifier.size(width.dp, height.dp),
                            painter = painter,
                            contentDescription = "image",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            name.toString(),
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.width(width.dp)
                                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
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


@Composable
fun Footer(modifier: Modifier, viewModel: HomeViewModel, onClicked: (String) -> Unit) {
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
        modifier = modifier.padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.1f),
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
                modifier = Modifier.weight(0.1f),
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
            val lazyState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            val customFlingBehavior = rememberSnappingFlingBehavior(lazyState, coroutineScope)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                state = lazyState,
                flingBehavior = customFlingBehavior,
                modifier = Modifier.weight(0.7f)
            ) {
                items(movieList) { movie ->
                    with(movie) {
                        val painter =
                            rememberAsyncImagePainter(makeURLRequestImage(posterUrl.toString()))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.clickable {
                                onClicked(slug.toString())
                            }
                        ) {
                            val ratio = 1.5 / 2.5
                            val height = getScreenSize().second * ratio * 0.5
                            val width = height * 3 / 4
                            Image(
                                modifier = Modifier.size(width.dp, height.dp),
                                painter = painter,
                                contentDescription = "image",
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                name.toString(),
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier.width(width.dp)
                                    .padding(top = 12.dp, start = 12.dp, end = 12.dp),
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

@Composable
fun rememberSnappingFlingBehavior(
    lazyListState: LazyListState,
    coroutineScope: CoroutineScope,
): FlingBehavior {
    val flingDecay = rememberSplineBasedDecay<Float>() // Standard fling decay

    return remember(lazyListState, flingDecay, coroutineScope) {
        object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                // 1. Calculate where the fling would naturally stop (the target scroll offset)
                val decayTargetScrollOffset = flingDecay.calculateTargetValue(0f, initialVelocity)

                // 2. Determine the current scroll position and item properties
                val currentScroll = lazyListState.firstVisibleItemScrollOffset.toFloat() +
                        lazyListState.firstVisibleItemIndex * (/* averageItemWidthPx (needs to be calculated or provided) */ 700f) // Placeholder for item width + spacing

                val targetScrollAfterFling = currentScroll + decayTargetScrollOffset

                // 3. Determine the target item and offset based on your snapping logic
                // (e.g., find the item whose start is closest to targetScrollAfterFling)
                // This is where you implement your logic for which item index to snap to.
                // For simplicity, let's assume itemWidthPx is known and items are uniformly sized.
                val itemWidthPlusSpacingPx =
                    700f // Placeholder: replace with actual average/fixed item width + spacing
                val targetIndex = (targetScrollAfterFling / itemWidthPlusSpacingPx).roundToInt()
                    .coerceIn(0, lazyListState.layoutInfo.totalItemsCount - 1)


                // 4. Animate to that target item and offset 0
                coroutineScope.launch { // Use the provided scope
                    lazyListState.animateScrollToItem(index = targetIndex, scrollOffset = 0)
                }

                // Return the velocity that we did not consume (0f if we handle all of it)
                return 0f
            }
        }
    }
}
