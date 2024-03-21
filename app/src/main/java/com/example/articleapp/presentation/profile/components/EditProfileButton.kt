package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.articleapp.presentation.theme.Typography

@Composable
fun EditProfileButton(
    onButtonClick : () -> Unit
) {
    OutlinedButton(onClick = { onButtonClick() }, content = {
        Text(text = "Edit Profile", style = Typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }, shape = RoundedCornerShape(20.dp))
}