package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeChooser(
    theme : String?,
    onSelectItem : (String) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val themeList = listOf("System Default", "Light", "Dark")
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Theme")
        Spacer(modifier = Modifier.weight(1f))
        Column {
            OutlinedCard(content = { Text(text = theme ?: "", modifier = Modifier.padding(10.dp)) }, onClick = {menuExpanded = !menuExpanded})
            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                themeList.forEach {theme ->
                    DropdownMenuItem(text = { Text(text = theme) }, onClick = {
                        onSelectItem(theme)
                        menuExpanded = false
                    })
                }
            }
        }
    }
}