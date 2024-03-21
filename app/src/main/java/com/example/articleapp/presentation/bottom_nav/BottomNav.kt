package com.example.articleapp.presentation.bottom_nav

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.articleapp.R
import com.example.articleapp.presentation.root.Screen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNav(
    navController: NavHostController,
    logout: () -> Unit
) {
    val items = listOf(
        BottomNavigationItem(
            route = Screen.Home.route,
            title = "Home",
            icon = ImageVector.vectorResource(id = R.drawable.home)
        ),
        BottomNavigationItem(
            route = Screen.Search.route,
            title = "Search",
            icon = ImageVector.vectorResource(id = R.drawable.search)
        ),
        BottomNavigationItem(
            route = Screen.ArticleGenerate.route,
            title = "Generate",
            icon = ImageVector.vectorResource(id = R.drawable.add)
        ),
        BottomNavigationItem(
            route = Screen.Saved.route,
            title = "Saved",
            icon = ImageVector.vectorResource(id = R.drawable.saved)
        ),
        BottomNavigationItem(
            route = Screen.Profile.route,
            title = "Profile",
            icon = ImageVector.vectorResource(id = R.drawable.profile)
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = { NavigationBar(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.CenterVertically).padding(horizontal = 10.dp),
                        shape = CircleShape,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route)
                        },
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        content = { Icon(imageVector = item.icon, contentDescription = item.title) }
                    )
                } else {
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title)
                        },
                        label = { Text(text = item.title)},
                        alwaysShowLabel = false)
                }
            }
        }
        }
    ) { innerPadding ->
        BottomNavGraph(navController = navController, innerPaddingValues = innerPadding) { logout() }
    }
}

data class BottomNavigationItem(
    val route : String,
    val title : String,
    val icon : ImageVector
)