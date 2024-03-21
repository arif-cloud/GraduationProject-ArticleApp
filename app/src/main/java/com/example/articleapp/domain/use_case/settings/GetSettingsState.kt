package com.example.articleapp.domain.use_case.settings

import android.content.SharedPreferences
import com.example.articleapp.presentation.profile.SettingsState
import javax.inject.Inject

class GetSettingsState @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke() : SettingsState {
        val theme = sharedPreferences.getString("theme", "System Default")
        val notificationState = sharedPreferences.getBoolean("notification", false)
        return SettingsState(theme, notificationState)
    }

}