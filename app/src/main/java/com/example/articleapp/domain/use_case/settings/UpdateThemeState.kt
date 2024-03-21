package com.example.articleapp.domain.use_case.settings

import android.content.SharedPreferences
import javax.inject.Inject

class UpdateThemeState @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(theme : String) {
        sharedPreferences.edit().putString("theme", theme).apply()
    }
}