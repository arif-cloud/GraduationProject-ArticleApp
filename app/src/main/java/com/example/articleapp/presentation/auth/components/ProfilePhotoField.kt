package com.example.articleapp.presentation.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.articleapp.R

@Composable
fun ProfilePhotoField(
    imageUrl: String?,
    onClick: () -> Unit
) {
    Box {
        AsyncImage(model = if (!imageUrl.isNullOrEmpty()) imageUrl else R.drawable.default_profile, contentDescription = null, modifier = Modifier.clip(CircleShape).size(200.dp), contentScale = ContentScale.Crop)
        IconButton(onClick = { onClick() }, modifier = Modifier
            .size(50.dp)
            .align(Alignment.BottomEnd)
            .clip(CircleShape),
            content = { Icon(
                painter = painterResource(id = R.drawable.edit),
                contentDescription = null, modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.onPrimary)
            }, colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary))
    }
}