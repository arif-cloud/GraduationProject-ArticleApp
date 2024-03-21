package com.example.articleapp.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.articleapp.R

@Composable
fun ArticleLogo() {
    Image(painter = painterResource(id = R.drawable.radar), contentDescription = "article_icon")
}