package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.articleapp.R

@Composable
fun SettingsTitle() {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = R.drawable.settings), contentDescription = "settings_icon", tint = MaterialTheme.colorScheme.onSurface)
        Text(text = "Settings", modifier = Modifier.padding(start = 5.dp), fontSize = 20.sp)
    }
}