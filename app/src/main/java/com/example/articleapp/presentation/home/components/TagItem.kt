package com.example.articleapp.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.articleapp.presentation.theme.Dark
import com.example.articleapp.presentation.theme.Light
import com.example.articleapp.presentation.theme.Typography

@Composable
fun TagItem(
    tag : String?
) {
    Card(shape = RoundedCornerShape(size = 100.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
        Text(text = ("#$tag") ?: "", modifier = Modifier.padding(horizontal = 8.dp), style = Typography.bodyMedium)
    }
}