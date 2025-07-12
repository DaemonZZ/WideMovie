package com.mp.widemovie.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.daemonz.base_sdk.model.Item
import com.daemonz.common.theme.FidoTheme
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.extensions.rememberAsyncImagePainter
import com.mp.widemovie.saveSelectedMovie
import com.mp.widemovie.ui.screen.MovieDetail
import com.mp.widemovie.utils.WindowType
import com.mp.widemovie.utils.getWindowType
import com.mp.widemovie.utils.makeURLRequestImage
import com.mp.widemovie.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.debounce
import org.jetbrains.compose.resources.painterResource
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.loading_animation

const val DELAY_TIME_SEARCH = 3000L

@Composable
fun SearchView(viewModel: SearchViewModel) {
    var inputSearchState by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        textStyle = TextStyle(color = Color.White),
        modifier = Modifier.fillMaxWidth(),
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
        leadingIcon = { Icon(Icons.Filled.Search, "Search icon") },
        // TODO voice search
        trailingIcon = {},
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

@Composable
internal fun LazyGridFilms(
    movieLists: List<Item>,
    nav: Navigator,
    modifier: Modifier = Modifier
) {
    val fixedColumn = when (getWindowType()) {
        WindowType.COMPACT -> 1
        WindowType.MEDIUM -> 2
        WindowType.EXPANDED -> 3
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(fixedColumn),
        contentPadding = PaddingValues( vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),   // Space between rows of items
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = movieLists.sortedByDescending { it.year }) { item ->
            BeautifulMovieListItem(
                item,
                modifier.fillMaxWidth().fillMaxHeight(0.2f)
            ) {
                saveSelectedMovie(item.convertToRecentSearch())
                nav += MovieDetail(item.slug.toString())
            }

        }
    }
}

@Composable
private fun BeautifulMovieListItem(
    movie: Item, // Pass your movie data object
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    val imageUrl = if (CurrentUIType == UIType.Android) {
        movie.thumbUrl.toString()
    } else {
        movie.posterUrl.toString()
    }
    val painter =
        rememberAsyncImagePainter(makeURLRequestImage(imageUrl)) {
            painterResource(Res.drawable.loading_animation)
        }
    with(movie) {
        Card(
            modifier = modifier
                .clickable {
                    onClicked()
                }, // Padding around the card
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp,
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp), // Padding inside the card
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Movie Poster Image
                Image(
                    painter = painter,
                    contentDescription = "$name poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(0.4f)
                        .aspectRatio(2f / 3f) // Common movie poster aspect ratio (e.g., width 2, height 3)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(0.7f), // Take up 70% of the Row's width
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = name,
                        style = FidoTheme.typography.h2,
                        fontWeight = FontWeight.Bold,
                        color = FidoTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Year: ${movie.year}",
                        style = FidoTheme.typography.body1,
                        color = FidoTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Type: $type",
                        style = FidoTheme.typography.body1,
                        color = FidoTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val countryName = country.firstOrNull()?.name ?: "Unknown"
                    Text(
                        text = "Country: $countryName",
                        style = FidoTheme.typography.body1,
                        color = FidoTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Quality: $quality",
                        style = FidoTheme.typography.body1,
                        color = FidoTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }

}
