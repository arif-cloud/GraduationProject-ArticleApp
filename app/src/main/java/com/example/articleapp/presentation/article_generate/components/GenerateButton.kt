package com.example.articleapp.presentation.article_generate.components

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.articleapp.R
import com.example.articleapp.presentation.article_generate.ArticleGenerateState

@Composable
fun GenerateButton(
    articleGenerateState: ArticleGenerateState,
    onClickGenerateButton : () -> Unit
) {
    Button(onClick = { onClickGenerateButton() }) {
        if (!articleGenerateState.isLoading) {
            Icon(painter = painterResource(id = R.drawable.generate), contentDescription = "generate_icon")
            Text(text = "Generate")
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}