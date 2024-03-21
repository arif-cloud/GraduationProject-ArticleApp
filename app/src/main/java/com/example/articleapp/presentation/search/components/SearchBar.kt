package com.example.articleapp.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.articleapp.R
import com.example.articleapp.presentation.search.SearchViewModel

@Composable
fun SearchBar(
    onSearch : (String) -> Unit,
    onCancel : () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            if (text.isNotEmpty())
                onSearch(text)
            else
                onCancel()
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "search_icon"
            )
        },
        trailingIcon = {
            if (text.isNotEmpty())
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "clear_icon",
                    modifier = Modifier.clickable {
                        text = ""
                        onCancel()
                    }
                )
        },
        placeholder = { Text(text = "Enter Article Name") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1
    )
}