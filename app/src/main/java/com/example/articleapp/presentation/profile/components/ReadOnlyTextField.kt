package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.articleapp.R

@Composable
fun ReadOnlyTextField(
    email : String
) {
    TextField(
        value = email,
        onValueChange = { /*TODO*/ },
        leadingIcon = { Icon(imageVector = ImageVector.vectorResource(id = R.drawable.email), contentDescription = null) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer, focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer)
    )
}