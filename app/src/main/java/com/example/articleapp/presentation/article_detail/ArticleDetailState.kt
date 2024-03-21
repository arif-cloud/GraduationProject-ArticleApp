package com.example.articleapp.presentation.article_detail

data class ArticleState(
    val saved : Boolean = false,
    val error : String? = null
)
