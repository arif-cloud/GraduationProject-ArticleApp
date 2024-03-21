package com.example.articleapp.presentation.home

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import com.example.articleapp.presentation.home.components.ArticleListItem
import com.example.articleapp.presentation.home.components.CategoryList
import com.example.articleapp.presentation.home.components.PaginatedArticleList

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeState.collectAsState()
    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(isGranted)
        }
    )
    LaunchedEffect(key1 = Unit) {
        notificationPermissionResultLauncher.launch(
            android.Manifest.permission.POST_NOTIFICATIONS
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.data?.let {homeData ->
                Text(text = "Hi, ${homeData.username} what do you want to read", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 48.dp))
                CategoryList(
                    categoryList = homeData.categoryList,
                    onSelectCategory = {categoryName ->
                        viewModel.getArticles(categoryName)
                    }
                )
                homeData.articleData?.let {flowPagingData ->
                    LaunchedEffect(key1 = Unit) {
                        viewModel.cacheData(homeData.username, homeData.categoryList, flowPagingData)
                    }
                    LaunchedEffect(key1 = flowPagingData) {
                        viewModel.updateCacheData(flowPagingData)
                    }
                    val articleList = flowPagingData.collectAsLazyPagingItems()
                    PaginatedArticleList(articleList = articleList, navController = navController)
                }
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

@Composable
fun HomeScreenContent() {
    val categoryList = listOf(
        Category(0,"Android"),
        Category(1,"Java"),
        Category(2,"Machine Learning"),
        Category(0,"IOS")
    )
    val articleList = listOf(
        Article(
            id = 0,
            title = "The Decorator Design Pattern is kind of like a waffle",
            imageUrl = "https://cdn-media-1.freecodecamp.org/images/1*4FU5faISak9BmmtnI12bpQ.jpeg",
            tags = listOf("Design Patterns"),
            publishDate = "Nov 25 2017",
            url = ""
        ),
        Article(
            id = 0,
            title = "The Decorator Design Pattern is kind of like a waffle",
            imageUrl = "https://cdn-media-1.freecodecamp.org/images/1*4FU5faISak9BmmtnI12bpQ.jpeg",
            tags = listOf("Design Patterns"),
            publishDate = "Nov 25 2017",
            url = ""
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Hi, 4rif what do you want to read", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 48.dp))
            CategoryList(categoryList = categoryList, onSelectCategory = { })
            LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(articleList) {article ->
                    ArticleListItem(article, onItemClick = {})
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevHomeScreen() {
    HomeScreenContent()
}