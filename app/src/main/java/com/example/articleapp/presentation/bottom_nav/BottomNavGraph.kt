package com.example.articleapp.presentation.bottom_nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.articleapp.common.Graph
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.root.Screen
import com.example.articleapp.presentation.article_detail.ArticleDetailScreen
import com.example.articleapp.presentation.article_detail.ArticleDetailViewModel
import com.example.articleapp.presentation.article_generate.ArticleGenerateScreen
import com.example.articleapp.presentation.article_generate.ArticleGenerateViewModel
import com.example.articleapp.presentation.home.HomeScreen
import com.example.articleapp.presentation.home.HomeViewModel
import com.example.articleapp.presentation.profile.ProfileScreen
import com.example.articleapp.presentation.profile.ProfileViewModel
import com.example.articleapp.presentation.saved.SavedScreen
import com.example.articleapp.presentation.saved.SavedViewModel
import com.example.articleapp.presentation.search.SearchScreen
import com.example.articleapp.presentation.search.SearchViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    innerPaddingValues: PaddingValues,
    logout: () -> Unit
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Graph.HOME,
        modifier = Modifier.padding(innerPaddingValues)
    ) {
        composable(route = Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel(viewModelStoreOwner)
            HomeScreen(navController, viewModel)
        }
        composable(route = Screen.Search.route) {
            val viewModel: SearchViewModel = hiltViewModel(viewModelStoreOwner)
            SearchScreen(navController, viewModel)
        }
        composable(route = Screen.Saved.route) {
            val viewModel: SavedViewModel = hiltViewModel(viewModelStoreOwner)
            SavedScreen(navController, viewModel)
        }
        composable(route = Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel(viewModelStoreOwner)
            ProfileScreen(viewModel) { logout() }
        }
        composable(route = Screen.ArticleDetail.route) {
            val viewModel: ArticleDetailViewModel = hiltViewModel(viewModelStoreOwner)
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            ArticleDetailScreen(navController = navController, article, viewModel)
        }
        composable(route = Screen.ArticleGenerate.route) {
            val viewModel: ArticleGenerateViewModel = hiltViewModel(viewModelStoreOwner)
            ArticleGenerateScreen(viewModel)
        }
    }
}