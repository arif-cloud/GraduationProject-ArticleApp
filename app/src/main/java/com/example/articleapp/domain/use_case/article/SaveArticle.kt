package com.example.articleapp.domain.use_case.article

import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class SaveArticle @Inject constructor(
    private val repository: ArticleRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(article: Article) : ArticleProcessResponse {
        return repository.saveArticle(article, firebaseRepository.getUserId())
    }
}