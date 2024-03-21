package com.example.articleapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.root.Screen

@Composable
fun PaginatedArticleList(
    articleList : LazyPagingItems<Article>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(articleList.itemCount) {index ->
            articleList[index]?.let {article ->
                ArticleListItem(article, onItemClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "article",
                        value = article
                    )
                    navController.navigate(Screen.ArticleDetail.route) })
            }
        }
        if (articleList.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth(
                    Alignment.CenterHorizontally))
            }
        }
    }
    if (articleList.loadState.refresh is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}