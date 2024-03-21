package com.example.articleapp.presentation.search

import com.example.articleapp.domain.model.PopularArticle

data class SearchState(
    val isLoading : Boolean = false,
    val data : List<PopularArticle>? = null,
    val error : String = ""
)
