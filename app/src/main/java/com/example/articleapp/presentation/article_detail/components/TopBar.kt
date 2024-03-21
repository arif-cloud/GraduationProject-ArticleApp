package com.example.articleapp.presentation.article_detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.articleapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    menuExpanded : Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    onRefreshClick : () -> Unit,
    onShareClick : () -> Unit,
    onClickBackButton : () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Article Detail", style = Typography.titleMedium) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ), navigationIcon = {
            IconButton(onClick = { onClickBackButton() }, content = { Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back_button") })
        }, actions = {
            IconButton(onClick = { onMenuExpandedChange(!menuExpanded) }, content = { Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "more_button") })
            DropdownMenu(expanded = menuExpanded, onDismissRequest = { onMenuExpandedChange(false) }) {
                DropdownMenuItem(text = { Text(text = "Refresh", style = Typography.bodyLarge) }, onClick = { onRefreshClick() }, leadingIcon = { Icon(imageVector = Icons.Filled.Refresh, contentDescription = "refresh_icon") })
                DropdownMenuItem(text = { Text(text = "Share", style = Typography.bodyLarge) }, onClick = { onShareClick() }, leadingIcon = { Icon(imageVector = Icons.Filled.Share, contentDescription = "share_icon") })
            }
        })
}