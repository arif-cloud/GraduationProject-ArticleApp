package com.example.articleapp.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.articleapp.domain.model.Category
import com.example.articleapp.presentation.home.HomeData
import com.example.articleapp.presentation.home.HomeViewModel

@Composable
fun CategoryList(
    categoryList : List<Category>,
    onSelectCategory : (String) -> Unit
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(categoryList) {index, category ->
            CategoryListItem(
                category = category,
                isSelected = index == selectedIndex,
                onItemClick = {category ->
                    selectedIndex = index
                    onSelectCategory(category.name)
                }
            )
        }
    }
}