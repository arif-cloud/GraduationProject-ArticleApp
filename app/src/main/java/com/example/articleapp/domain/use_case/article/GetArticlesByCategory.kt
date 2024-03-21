package com.example.articleapp.domain.use_case.article

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.articleapp.data.remote.paging.ArticlePagingSource
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesByCategory @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(category : String) : Flow<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 1)){
            ArticlePagingSource(repository, category)
        }.flow
    }
}