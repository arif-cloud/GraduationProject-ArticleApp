package com.example.articleapp.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.articleapp.presentation.profile.components.AccountInfoSection
import com.example.articleapp.presentation.profile.components.EditProfileButton
import com.example.articleapp.presentation.profile.components.EditProfileDialog
import com.example.articleapp.presentation.profile.components.LogOutButton
import com.example.articleapp.presentation.profile.components.SettingsSection

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    logout: () -> Unit
) {
    val state by viewModel.profileState.collectAsState()
    val context = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            state.profileData.accountInfo?.let {
                AccountInfoSection(accountInfo = it)
            }
            EditProfileButton(
                onButtonClick = { openAlertDialog = true }
            )
            Spacer(modifier = Modifier.height(10.dp))
            state.profileData.settingsState?.let {
                SettingsSection(
                    settingsState = it,
                    onThemeChange = {theme ->
                        viewModel.updateThemeStatus(theme)
                    },
                    onNotificationChange = {isGranted ->
                        viewModel.updateNotificationStatus(isGranted)
                    }
                )
            }
            LogOutButton(onClick = {
                viewModel.logOut()
                logout()
            })
        }
        if (openAlertDialog) {
            EditProfileDialog(
                username = state.profileData.accountInfo?.username ?: "",
                email = state.profileData.accountInfo?.email ?: "",
                password = "",
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {username, password ->
                    viewModel.updateProfile(
                        username,
                        password,
                        onSuccess = {text ->
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                            openAlertDialog = false
                        },
                        onFailure = {error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
        if (state.error.isNotEmpty()) {
            Text(text = state.error, modifier = Modifier.align(Alignment.Center), color = Color.Red)
        }
    }
}