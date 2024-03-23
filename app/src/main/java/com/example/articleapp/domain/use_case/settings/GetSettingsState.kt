package com.example.articleapp.domain.use_case.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import com.example.articleapp.presentation.profile.SettingsState
import javax.inject.Inject

class GetSettingsState @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    operator fun invoke() : SettingsState {
        val theme = sharedPreferences.getString("theme", "System Default")
        val notificationState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
           true
        }
        return SettingsState(theme, notificationState)
    }

}