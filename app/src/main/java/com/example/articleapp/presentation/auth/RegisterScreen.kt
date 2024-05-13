package com.example.articleapp.presentation.auth

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articleapp.presentation.auth.components.EmailTextField
import com.example.articleapp.presentation.auth.components.PasswordTextField
import com.example.articleapp.presentation.auth.components.ProfilePhotoField
import com.example.articleapp.presentation.auth.components.UsernameTextField

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel : AuthViewModel = hiltViewModel()
) {
    val state = viewModel.authState.value
    val context = LocalContext.current
    LaunchedEffect(key1 = state.isSuccess) {
        if(state.isSuccess) {
            Toast.makeText(context, "Create account successful", Toast.LENGTH_LONG).show()
            navController.popBackStack()
            viewModel.resetState()
        }
    }
    LaunchedEffect(key1 = state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
        }
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ProfilePhotoField(imageUri = imageUri, onClick = {
                photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            })
            UsernameTextField(username = username, onUsernameChange = { username = it })
            EmailTextField(email = email, onEmailChange = { email = it })
            PasswordTextField(password = password, onPasswordChange = { password = it })
            Button(
                onClick = { viewModel.register(username, imageUri, email, password) },
                modifier = Modifier.fillMaxWidth(),
                content = { Text(text = "Create") }
            )
        }
    }
}