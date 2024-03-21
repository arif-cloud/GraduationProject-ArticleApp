package com.example.articleapp.presentation.home

import androidx.paging.PagingData
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val isLoading : Boolean = false,
    val data : HomeData? =  null,
    val error : String = ""
)

data class HomeData(
    val username : String = "",
    val categoryList : List<Category> = emptyList(),
    val articleData : Flow<PagingData<Article>>? = null
)
