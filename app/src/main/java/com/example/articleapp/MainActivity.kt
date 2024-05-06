package com.example.articleapp

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.articleapp.presentation.root.RootNavigationGraph
import com.example.articleapp.presentation.theme.ArticleAppTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var notificationDestination: String? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val destination = intent.getStringExtra("destination")
        if (!destination.isNullOrEmpty()) {
            notificationDestination = destination
        }
        setContent {
            var themeState by remember { mutableStateOf(sharedPreferences.getString("theme", "System Default")) }
            var dynamicColor = themeState.equals("System Default")
            var darkTheme = themeState.equals("Dark")
            sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
                if (key == "theme") {
                    themeState = sharedPreferences.getString(key, "")
                }
            }
            LaunchedEffect(themeState) {
                dynamicColor = themeState.equals("System Default")
                darkTheme = themeState.equals("Dark")
            }
            ArticleAppTheme(darkTheme = if (dynamicColor) isSystemInDarkTheme() else darkTheme, dynamicColor = dynamicColor) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    RootNavigationGraph(firebaseAuth, navController, notificationDestination)
                }
            }
        }
    }
}