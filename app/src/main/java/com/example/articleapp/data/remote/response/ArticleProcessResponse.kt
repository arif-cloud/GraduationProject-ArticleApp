package com.example.articleapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleProcessResponse(
    @SerializedName("status_code")
    val statusCode : Int,
    @SerializedName("status_message")
    val statusMessage : String
)
