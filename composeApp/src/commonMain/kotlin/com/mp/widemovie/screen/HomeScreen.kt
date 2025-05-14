package com.mp.widemovie.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.daemonz.base_sdk.utils.TLog
import com.mp.widemovie.CurrentUIType
import com.mp.widemovie.UIType
import com.mp.widemovie.base.BaseScreen
import com.mp.widemovie.viewmodel.HomeViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import widemovie.composeapp.generated.resources.Res
import widemovie.composeapp.generated.resources.compose_multiplatform
import widemovie.composeapp.generated.resources.hint_search
import widemovie.composeapp.generated.resources.ic_search

class HomeScreen : BaseScreen() {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = koinViewModel()
        val categoryUI = CurrentUIType
        MaterialTheme {
            Scaffold(
                bottomBar = { if (categoryUI == UIType.Android) AndroidBottomBar() },
                drawerBackgroundColor = Color.Transparent,
                drawerElevation = 0.dp,
                drawerContent = { if (categoryUI == UIType.Web) WebNavigatorDrawer() },
                drawerGesturesEnabled = (categoryUI == UIType.Web)
            ) {
                var showContent by remember { mutableStateOf(false) }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(50.dp))
                    SearchView(
                        onSearch = {
                            // logic here
                        }
                    )
                    Button(onClick = {
                        nav += MovieDetail("natra")
                    }) {
                        Text("Click me!")
                    }
                    AnimatedVisibility(showContent) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painterResource(Res.drawable.compose_multiplatform),
                                null
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
fun SearchView(
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    LaunchedEffect(key1 = query) {
        snapshotFlow { query }
            .debounce(500L)
            .distinctUntilChanged()
            .collect { debouncedQuery ->
                if (debouncedQuery.isNotEmpty()) {
                    onSearch(debouncedQuery)
                }
            }
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 8.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { currentQuery ->
                    TLog.d("SearchView", "query: $query - currentQuery: $currentQuery")
                    query = currentQuery
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    if (!isFocused) {
                        Text(
                            text = stringResource(Res.string.hint_search),
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.isNotEmpty()) {
                            onSearch(query)
                        }
                    },
                    onDone = {
                        if (query.isNotEmpty()) {
                            onSearch(query)
                        }
                    }
                ),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                interactionSource = interactionSource
            )
            if (!isFocused) {
                Icon(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(width = 32.dp, height = 32.dp)
                        .align(Alignment.CenterStart)
                        .padding(start = 12.dp),
                    tint = Color.Red
                )
            }
        }
    }
}