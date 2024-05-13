package com.example.articleapp.domain.use_case.auth

import android.content.SharedPreferences
import javax.inject.Inject

class GetLoginMethod @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke() : String? {
        return sharedPreferences.getString("login_method", null)
    }
}