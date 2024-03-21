package com.example.articleapp.domain.use_case.article

import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.domain.repository.ArticleRepository
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class RemoveArticle @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(articleId: Int) : ArticleProcessResponse {
        return articleRepository.removeArticle(articleId, firebaseRepository.getUserId())
    }
}