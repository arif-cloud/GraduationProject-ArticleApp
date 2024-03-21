package com.example.articleapp.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.articleapp.domain.model.Category

@Composable
fun CategoryListItem(
    category: Category,
    isSelected : Boolean,
    onItemClick : (Category) -> Unit
) {
    Card(shape = RoundedCornerShape(size = 100.dp), colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer), modifier = Modifier.padding(end = 8.dp).clickable { onItemClick(category) }) {
        Text(text = category.name, style = MaterialTheme.typography.bodyLarge, color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp))
    }
}