package com.example.articleapp.presentation.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.articleapp.R

@Composable
fun UsernameTextField(
    username : String,
    onUsernameChange: (String) -> Unit
) {
    TextField(
        value = username,
        onValueChange = { onUsernameChange(it) },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.person),
                contentDescription = null
            )
        },
        placeholder = { Text(text = "Username") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        maxLines = 1
    )
}