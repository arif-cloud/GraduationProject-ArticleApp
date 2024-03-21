package com.example.articleapp.presentation.bottom_nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun BottomNavHostScreen(
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit
) {
    BottomNav(navController = navController) { logout() }
}