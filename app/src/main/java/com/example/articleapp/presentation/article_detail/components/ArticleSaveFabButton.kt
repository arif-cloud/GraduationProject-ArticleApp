package com.example.articleapp.presentation.article_detail.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.articleapp.R
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.article_detail.ArticleDetailViewModel
import com.example.articleapp.presentation.article_detail.ArticleState

@Composable
fun ArticleSaveFabButton(
    articleState : ArticleState,
    onClickSaveButton : () -> Unit
) {
    FloatingActionButton(
        onClick = { onClickSaveButton() },
        containerColor = MaterialTheme.colorScheme.primary,
        content = { Icon(painter = if (articleState.saved) painterResource(id = R.drawable.saved_filled) else painterResource(id = R.drawable.saved_outlined), "Save Article!") }
    )
}