package com.example.articleapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articleapp.common.Graph
import com.example.articleapp.presentation.root.Screen
import com.example.articleapp.presentation.auth.components.ArticleLogo
import com.example.articleapp.presentation.auth.components.EmailTextField
import com.example.articleapp.presentation.auth.components.PasswordTextField

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel : AuthViewModel = hiltViewModel()
) {
    val state = viewModel.authState.value
    val context = LocalContext.current
    LaunchedEffect(key1 = state.isSuccess) {
        if(state.isSuccess) {
            Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
            navController.navigate(Graph.HOME)
            viewModel.resetState()
        }
    }
    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ArticleLogo()
            Spacer(modifier = Modifier.height(20.dp))
            EmailTextField(email = email, onEmailChange = { email = it })
            PasswordTextField(password = password, onPasswordChange = { password = it })
            Button(onClick = { viewModel.login(email, password) }, modifier = Modifier.fillMaxWidth()) { Text(text = "Login") }
            Text(text = "Create An Account", modifier = Modifier.clickable { navController.navigate(
                Screen.Register.route) })
        }
    }
}