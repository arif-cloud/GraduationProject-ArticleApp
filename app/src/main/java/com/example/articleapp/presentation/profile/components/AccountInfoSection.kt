package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.presentation.theme.Typography

@Composable
fun AccountInfoSection(
    accountInfo : AccountInfo
) {
    AsyncImage(
        model = accountInfo.imageUri,
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .size(200.dp),
        contentScale = ContentScale.Crop
    )
    Text(text = accountInfo.username ?: "", style = Typography.bodyLarge)
    Text(text = accountInfo.email ?: "", style = Typography.bodyMedium)
}