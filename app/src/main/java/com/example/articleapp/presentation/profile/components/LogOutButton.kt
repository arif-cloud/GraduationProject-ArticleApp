package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.articleapp.R

@Composable
fun LogOutButton(
    onClick : () -> Unit
) {
    Button(onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = MaterialTheme.colorScheme.onSurface), modifier = Modifier.fillMaxWidth(), content = {
        Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "logout_icon")
        Text(text = "Log Out", modifier = Modifier.padding(start = 5.dp))
    })
}