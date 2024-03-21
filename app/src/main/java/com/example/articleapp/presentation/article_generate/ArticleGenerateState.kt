package com.example.articleapp.presentation.article_generate


data class ArticleGenerateState(
    val isLoading : Boolean = false,
    val data : String? =  null,
    val error : String = ""
)
