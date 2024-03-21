package com.example.articleapp.domain.model

import com.google.gson.annotations.SerializedName

data class SavedArticle(
    @SerializedName("userId")
    val userId : String,
    @SerializedName("article")
    val article: Article
)
