package com.example.articleapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.articleapp.presentation.auth.components.EmailTextField
import com.example.articleapp.presentation.auth.components.PasswordTextField
import com.example.articleapp.presentation.auth.components.ProfilePhotoField
import com.example.articleapp.presentation.auth.components.UsernameTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    var imageUrl by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ProfilePhotoField(imageUrl = imageUrl, onClick = { showBottomSheet = true })
            UsernameTextField(username = username, onUsernameChange = { username = it })
            EmailTextField(email = email, onEmailChange = { email = it })
            PasswordTextField(password = password, onPasswordChange = { password = it })
            Button(onClick = { viewModel.register(username, imageUrl, email, password) }, modifier = Modifier.fillMaxWidth()) { Text(text = "Create") }
        }
        if (showBottomSheet) {
            ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState,
                content = {
                    LazyVerticalGrid(columns = GridCells.Fixed(count = 4), verticalArrangement = Arrangement.spacedBy(space = 8.dp), horizontalArrangement = Arrangement.spacedBy(space = 8.dp)) {
                        items(viewModel.getAvatarPhotos()) {
                            AsyncImage(model = it, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.clip(CircleShape).clickable {
                                imageUrl = it
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            })
                        }
                    }
                })
        }
    }
}