package com.example.articleapp.presentation.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotificationSwitcher(
    notificationChecked : Boolean,
    onSwitch : (Boolean) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Send Notification")
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = notificationChecked,
            onCheckedChange = { onSwitch(it) },
            thumbContent = if (notificationChecked) {
                { Icon(imageVector = Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(SwitchDefaults.IconSize), tint = MaterialTheme.colorScheme.primary) }
            } else { null }
        )
    }
}