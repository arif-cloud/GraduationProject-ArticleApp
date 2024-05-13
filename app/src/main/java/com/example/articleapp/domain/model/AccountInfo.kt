package com.example.articleapp.domain.model

data class AccountInfo(
    val userId : String = "",
    val username : String? = null,
    val imageUri : String? = null,
    val email : String? = null
)
