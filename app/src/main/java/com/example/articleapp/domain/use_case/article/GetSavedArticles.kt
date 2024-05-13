package com.example.articleapp.domain.use_case.article

import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetSavedArticles @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke() : List<Article> {
        return articleRepository.getSavedArticles(firebaseRepository.getUserId())
    }
}