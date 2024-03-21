package com.example.articleapp.presentation.profile

import com.example.articleapp.domain.model.AccountInfo

data class ProfileState(
    val profileData: ProfileData = ProfileData(),
    val error : String = ""
)

data class ProfileData(
    val accountInfo : AccountInfo? = null,
    val settingsState: SettingsState? = null
)
