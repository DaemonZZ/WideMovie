package com.mp.widemovie.utils

import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daemonz.base_sdk.IMG_BASE_URL
import com.daemonz.base_sdk.model.Item
import com.mp.widemovie.UIType
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.getScreenSize
import com.mp.widemovie.ui.screen.home.UiType.currentType
import com.mp.widemovie.utils.customviews.SelectiveCornerRoundingShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.loading_animation
import kotlin.math.roundToInt

fun makeURLRequestImage(thumbUrl: String) = "$IMG_BASE_URL/uploads/movies/$thumbUrl"

@Composable
fun ColumnScope.MovieLazyColumn(
    modifier: Modifier,
    imageSize: Float = 1f,
    movieList: List<Item>,
    onClicked: (Item) -> Unit
) {
    val lazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val customFlingBehavior = rememberSnappingFlingBehavior(lazyState, coroutineScope)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyState,
        flingBehavior = customFlingBehavior,
        modifier = modifier
    ) {
        items(movieList) { movie ->
            with(movie) {
                val thumbUrl = if (currentType == UIType.Android) {
                    thumbUrl
                } else {
                    posterUrl
                }
                val painter =
                    rememberAsyncImagePainter(makeURLRequestImage(thumbUrl.toString())) {
                        painterResource(Res.drawable.loading_animation)
                    }
                var parentSize by remember { mutableStateOf(IntSize.Zero) }
                val density = LocalDensity.current
                Box(
                    modifier = Modifier.fillMaxHeight(imageSize).aspectRatio(3f / 4f)
                        .clip(
                            SelectiveCornerRoundingShape(
                                cornerRadius = 16.dp,
                                clipTopEnd = true,  // Round this corner
                                clipBottomStart = true  // Round this corner
                            )
                        ).clickable {
                            onClicked(this)
                        }.onSizeChanged { newSize ->
                            parentSize = newSize
                        }
                ) {
                    val parentWidthDp = with(density) { parentSize.width.toDp() }
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        name.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.width(parentWidthDp)
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 12.dp)
                            .align(Alignment.BottomCenter),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                    )
                    if (quality.isNotEmpty()) {
                        Text(
                            quality.toString(),
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopStart)
                                .background(Color.Black.copy(0.7f))
                                .padding(4.dp),
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (episodeCurrent.isNotEmpty()) {
                        Text(
                            (episodeCurrent + "\n" + time),
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopEnd)
                                .clip(
                                    SelectiveCornerRoundingShape(
                                        cornerRadius = 16.dp,
                                        clipBottomStart = true,  // Round this corner
                                    )
                                )
                                .background(Color.Black.copy(0.7f))
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            maxLines = 2,
                            textAlign = TextAlign.Center
                        )
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

@Composable
fun getWindowType(): WindowType {
    val windowSize = getScreenSize()
    val width = windowSize.first
    return when (width) {
        in 0..599 -> {
            WindowType.COMPACT
        }

        in 600..904 -> {
            WindowType.MEDIUM
        }

        in 905..Int.MAX_VALUE -> {
            WindowType.EXPANDED
        }

        else -> {
            WindowType.COMPACT
        }
    }
}

enum class WindowType(val type: String) {
    COMPACT("COMPACT_SCREEN"),
    MEDIUM("MEDIUM_SCREEN"),
    EXPANDED("EXPANDED_SCREEN")
}
