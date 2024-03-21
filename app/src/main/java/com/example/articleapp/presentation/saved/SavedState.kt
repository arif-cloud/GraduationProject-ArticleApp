package com.example.articleapp.presentation.saved

import com.example.articleapp.domain.model.Article

data class SavedState(
    val isLoading : Boolean = false,
    val data : List<Article>? = null,
    val error : String = ""
)
