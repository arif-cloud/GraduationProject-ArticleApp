package com.example.articleapp.presentation.root

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.articleapp.common.Graph
import com.example.articleapp.presentation.auth.authNavGraph
import com.example.articleapp.presentation.bottom_nav.BottomNav
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavigationGraph(
    firebaseAuth: FirebaseAuth,
    navController: NavHostController,
    notificationDestination: String?
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if (firebaseAuth.currentUser != null) Graph.HOME else Graph.AUTHENTICATION
    ) {
        authNavGraph(navController)
        composable(route = Graph.HOME) {
            BottomNav(notificationDestination, logout = { navController.navigate(Graph.AUTHENTICATION) })
        }
    }
}
