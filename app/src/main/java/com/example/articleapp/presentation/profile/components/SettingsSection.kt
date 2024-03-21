package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.articleapp.presentation.profile.SettingsState

@Composable
fun SettingsSection(
    settingsState : SettingsState,
    onThemeChange : (String) -> Unit,
    onNotificationChange : (Boolean) -> Unit
) {
    var theme by remember { mutableStateOf(settingsState.theme) }
    var notificationChecked by remember { mutableStateOf(settingsState.notification) }
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(color = MaterialTheme.colorScheme.primaryContainer)) {
        SettingsTitle()
        Divider(modifier = Modifier.fillMaxWidth().height(4.dp))
        ThemeChooser(theme = theme, onSelectItem = {
            theme = it
            onThemeChange(it)
        })
        NotificationSwitcher(notificationChecked = notificationChecked, onSwitch = {
            notificationChecked = it
            onNotificationChange(it)
        })
    }
}