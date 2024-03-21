package com.example.articleapp.domain.model


import com.google.gson.annotations.SerializedName

data class PopularArticle(
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("publishDate")
    val publishDate: String?,
    @SerializedName("rank")
    val rank: Int?,
    @SerializedName("reactions")
    val reactions: Int?,
    @SerializedName("tags")
    val tags: List<String?>,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)