package com.example.articleapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.root.Screen

@Composable
fun ArticleList(
    articleList: List<Article>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(articleList) {article ->
            ArticleListItem(article, onItemClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "article",
                    value = article
                )
                navController.navigate(Screen.ArticleDetail.route)
            })
        }
    }
}
