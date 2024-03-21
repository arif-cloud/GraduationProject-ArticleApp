package com.example.articleapp.presentation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.articleapp.common.Graph
import com.example.articleapp.presentation.root.Screen

fun NavGraphBuilder.authNavGraph(navController : NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
    }
}