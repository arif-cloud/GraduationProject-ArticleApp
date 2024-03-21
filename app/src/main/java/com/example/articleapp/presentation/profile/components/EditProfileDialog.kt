package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.articleapp.presentation.auth.components.PasswordTextField
import com.example.articleapp.presentation.auth.components.UsernameTextField


@Composable
fun EditProfileDialog(
    username : String,
    email : String,
    password : String,
    onDismissRequest: () -> Unit,
    onConfirmation : (String, String) -> Unit
) {
    var newUsername by remember { mutableStateOf(username) }
    var newPassword by remember { mutableStateOf(password) }
    AlertDialog(
        title = { Text(text = "Edit Profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                UsernameTextField(username = newUsername, onUsernameChange = { newUsername = it })
                ReadOnlyTextField(email = email)
                PasswordTextField(password = newPassword, onPasswordChange = { newPassword = it })
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(onClick = { onConfirmation(newUsername, newPassword) }) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    )
}