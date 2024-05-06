package com.example.articleapp.domain.repository

import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.data.remote.response.ArticleStateResponse
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import com.example.articleapp.domain.model.PopularArticle

interface ArticleRepository {

    suspend fun getAllArticles() : List<Article>

    suspend fun getDailyArticle(dayIndex: Int) : Article

    suspend fun getArticlesByCategory(category : String, page : Int) : List<Article>

    suspend fun getAllCategories() : List<Category>

    suspend fun getPopularArticles() : List<PopularArticle>

    suspend fun getArticlesBySearch(q : String) : List<Article>

    suspend fun saveArticle(article: Article, userId: String) : ArticleProcessResponse

    suspend fun removeArticle(articleId: Int, userId: String) : ArticleProcessResponse

    suspend fun getSavedArticles(userId : String) : List<Article>

    suspend fun getArticleState(articleId : Int, userId : String) : ArticleStateResponse

}