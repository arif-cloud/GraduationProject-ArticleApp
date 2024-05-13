package com.example.articleapp.domain.use_case.auth

import android.content.SharedPreferences
import javax.inject.Inject

class UpdateLoginMethod @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(loginMethod : String?) {
        sharedPreferences.edit().putString("login_method", loginMethod).apply()
    }
}