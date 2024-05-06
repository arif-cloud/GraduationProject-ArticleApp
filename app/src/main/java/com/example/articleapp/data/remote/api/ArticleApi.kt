package com.example.articleapp.data.remote.api

import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.data.remote.response.ArticleStateResponse
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import com.example.articleapp.domain.model.PopularArticle
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {

    @GET("articles/all")
    suspend fun getAllArticles() : List<Article>

    @GET("article/daily")
    suspend fun getDailyArticle(@Query("dayIndex") dayIndex : Int) : Article

    @GET("articles/{category}")
    suspend fun getArticlesByCategory(@Path("category") category : String, @Query("page") page : Int) : List<Article>

    @GET("categories")
    suspend fun getAllCategories() : List<Category>

    @GET("articles/popular")
    suspend fun getPopularArticles() : List<PopularArticle>

    @GET("articles/search")
    suspend fun getArticlesBySearch(@Query("q") q : String) : List<Article>

    @POST("article/save")
    suspend fun saveArticle(@Body article: Article, @Query("userId") userId : String) : ArticleProcessResponse

    @POST("article/{article_id}/remove")
    suspend fun removeArticle(@Path("article_id") articleId : Int, @Query("userId") userId : String) : ArticleProcessResponse

    @GET("articles/saved")
    suspend fun getSavedArticles(@Query("userId") userId : String) : List<Article>

    @GET("article/{article_id}/state")
    suspend fun getArticleState(@Path("article_id") articleId : Int, @Query("userId") userId : String) : ArticleStateResponse

}