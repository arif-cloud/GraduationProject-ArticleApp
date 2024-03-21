package com.example.articleapp.domain.use_case.article

import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesBySearch @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(q : String) : List<Article> {
        return repository.getArticlesBySearch(q)
    }
}