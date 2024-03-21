package com.example.articleapp.domain.use_case.article

import com.example.articleapp.domain.model.PopularArticle
import com.example.articleapp.domain.repository.ArticleRepository
import javax.inject.Inject

class GetPopularArticles @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() : List<PopularArticle> {
        return repository.getPopularArticles()
    }
}