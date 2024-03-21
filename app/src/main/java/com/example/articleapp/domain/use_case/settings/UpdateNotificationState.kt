package com.example.articleapp.domain.use_case.settings

import android.content.SharedPreferences
import javax.inject.Inject

class UpdateNotificationState @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(notificationState : Boolean) {
        sharedPreferences.edit().putBoolean("notification", notificationState).apply()
    }
}