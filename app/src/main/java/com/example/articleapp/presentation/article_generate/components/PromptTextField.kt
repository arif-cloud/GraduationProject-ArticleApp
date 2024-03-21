package com.example.articleapp.presentation.article_generate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.articleapp.presentation.theme.LightC

@Composable
fun PromptTextField(
    prompt : String,
    onPromptChange: (String) -> Unit
) {
    BasicTextField(value = prompt, onValueChange = { onPromptChange(it) }, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(color = LightC)
        .padding(10.dp), minLines = 4, maxLines = 6, textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface))
}