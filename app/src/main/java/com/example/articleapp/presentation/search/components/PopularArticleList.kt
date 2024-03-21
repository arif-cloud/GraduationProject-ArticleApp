package com.example.articleapp.presentation.search.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.articleapp.domain.model.PopularArticle
import com.example.articleapp.presentation.root.Screen

@Composable
fun PopularArticleList(
    articleList : List<PopularArticle>,
    navController : NavController
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(articleList) {article ->
            PopularArticleItem(article, onItemClick = {
                navController.navigate(Screen.ArticleDetail.route + "/${Uri.encode(it.url)}") })
        }
    }
}