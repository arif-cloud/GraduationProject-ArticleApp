package com.example.articleapp.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.articleapp.presentation.home.components.ArticleList
import com.example.articleapp.presentation.search.components.PopularArticleList
import com.example.articleapp.presentation.search.components.SearchBar


@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val state by viewModel.searchState.collectAsState()
    val searchArticleListState by viewModel.searchArticleListState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        state.data?.let {popularArticles ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SearchBar(
                    onSearch = {text ->
                        viewModel.searchArticle(text)
                    },
                    onCancel = {
                        viewModel.resetSearchedArticleListState()
                    }
                )
                if (searchArticleListState.isNullOrEmpty())
                    PopularArticleList(articleList = popularArticles, navController = navController)
                else
                    searchArticleListState?.let {
                        ArticleList(articleList = it, navController = navController)
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