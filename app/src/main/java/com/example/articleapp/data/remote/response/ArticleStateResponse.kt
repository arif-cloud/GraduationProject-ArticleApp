package com.example.articleapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleStateResponse (
    @SerializedName("id")
    val id : Int?,
    @SerializedName("saved")
    val saved : Boolean
)