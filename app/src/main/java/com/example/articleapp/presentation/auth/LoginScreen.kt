package com.example.articleapp.presentation.auth

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articleapp.R
import com.example.articleapp.common.Graph
import com.example.articleapp.presentation.auth.components.EmailTextField
import com.example.articleapp.presentation.auth.components.PasswordTextField
import com.example.articleapp.presentation.root.Screen
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state = viewModel.authState.value
    val context = LocalContext.current
    val signInClient = Identity.getSignInClient(context)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK)
                viewModel.googleSignIn(
                    intent = result.data ?: return@rememberLauncherForActivityResult,
                    oneTapClient = signInClient
                )
        }
    )
    LaunchedEffect(key1 = state.isSuccess) {
        if (state.isSuccess) {
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
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.onboard),
            contentDescription = "onboard_image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(40.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EmailTextField(email = email, onEmailChange = { email = it })
            PasswordTextField(password = password, onPasswordChange = { password = it })
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
            Button(
                onClick = {
                    viewModel.getIntentSender(signInClient) { signInIntentSender ->
                        signInIntentSender?.let { launcher.launch(IntentSenderRequest.Builder(it).build()) }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    tint = Color.Unspecified,
                    contentDescription = "google_icon"
                )
                Text(text = "Login with Google", modifier = Modifier.padding(start = 10.dp))
            }
            Text(text = "Create An Account", modifier = Modifier.clickable {
                navController.navigate(Screen.Register.route)
            })
        }
    }
}