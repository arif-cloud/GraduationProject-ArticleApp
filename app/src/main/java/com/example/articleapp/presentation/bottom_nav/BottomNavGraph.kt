package com.example.articleapp.presentation.bottom_nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.articleapp.common.Graph
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.root.Screen
import com.example.articleapp.presentation.article_detail.ArticleDetailScreen
import com.example.articleapp.presentation.article_generate.ArticleGenerateScreen
import com.example.articleapp.presentation.home.HomeScreen
import com.example.articleapp.presentation.profile.ProfileScreen
import com.example.articleapp.presentation.saved.SavedScreen
import com.example.articleapp.presentation.search.SearchScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    innerPaddingValues: PaddingValues,
    logout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Graph.HOME,
        modifier = Modifier.padding(innerPaddingValues)
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController)
        }
        composable(route = Screen.Saved.route) {
            SavedScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen { logout() }
        }
        composable(route = Screen.ArticleDetail.route) {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            ArticleDetailScreen(navController = navController, article)
        }
        composable(route = Screen.ArticleGenerate.route) {
            ArticleGenerateScreen()
        }
    }
}