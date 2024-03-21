package com.example.articleapp.presentation.root

sealed class Screen(val route : String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Search : Screen("search")
    object Saved : Screen("saved")
    object Profile : Screen("profile")
    object ArticleDetail : Screen("article_detail")
    object ArticleGenerate : Screen("article_generate")
}
