package com.example.articleapp.presentation.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articleapp.R
import com.example.articleapp.presentation.home.components.ArticleList
import com.example.articleapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = hiltViewModel()
) {
    val state by viewModel.savedState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Saved Articles", style = Typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                actions = { IconButton(onClick = { viewModel.fetchData() }, content = { Icon(imageVector = Icons.Filled.Refresh, contentDescription = "refresh_button") })})
        },
        content = {innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 10.dp)
            ) {
                state.data?.let {articles ->
                    if (articles.isNotEmpty()) {
                        ArticleList(articleList = articles, navController = navController)
                    } else {
                        Icon(painter = painterResource(id = R.drawable.empty_box), contentDescription = "empty_box", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.align(Alignment.Center))
                    }
                    LaunchedEffect(key1 = Unit) {
                        viewModel.cacheData(articles)
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                if (state.error.isNotEmpty()) {
                    Text(text = state.error, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}