package com.example.articleapp.data.remote.implementation

import com.example.articleapp.data.remote.api.ArticleApi
import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.data.remote.response.ArticleStateResponse
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import com.example.articleapp.domain.model.PopularArticle
import com.example.articleapp.domain.repository.ArticleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : ArticleRepository {

    override suspend fun getAllArticles(): List<Article> = withContext(ioDispatcher) {
        api.getAllArticles()
    }

    override suspend fun getArticlesByCategory(category: String, page : Int): List<Article> = withContext(ioDispatcher) {
        api.getArticlesByCategory(category, page)
    }

    override suspend fun getAllCategories(): List<Category> = withContext(ioDispatcher) {
        api.getAllCategories()
    }

    override suspend fun getPopularArticles(): List<PopularArticle> = withContext(ioDispatcher) {
        api.getPopularArticles()
    }

    override suspend fun getArticlesBySearch(q: String): List<Article> = withContext(ioDispatcher) {
        api.getArticlesBySearch(q)
    }

    override suspend fun saveArticle(article: Article, userId: String): ArticleProcessResponse = withContext(ioDispatcher) {
        api.saveArticle(article, userId)
    }

    override suspend fun removeArticle(articleId: Int, userId: String): ArticleProcessResponse = withContext(ioDispatcher) {
        api.removeArticle(articleId, userId)
    }

    override suspend fun getSavedArticles(userId: String): List<Article> = withContext(ioDispatcher) {
        api.getSavedArticles(userId)
    }

    override suspend fun getArticleState(articleId: Int, userId: String): ArticleStateResponse = withContext(ioDispatcher) {
        api.getArticleState(articleId, userId)
    }

}