package com.example.articleapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.domain.use_case.auth.GetAccountInfo
import com.example.articleapp.domain.use_case.auth.GetLoginMethod
import com.example.articleapp.domain.use_case.auth.SignOut
import com.example.articleapp.domain.use_case.auth.UpdateAccountInfo
import com.example.articleapp.domain.use_case.auth.UpdateLoginMethod
import com.example.articleapp.domain.use_case.settings.GetSettingsState
import com.example.articleapp.domain.use_case.settings.UpdateNotificationState
import com.example.articleapp.domain.use_case.settings.UpdateThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountInfo: GetAccountInfo,
    private val getSettingsState: GetSettingsState,
    private val updateThemeState: UpdateThemeState,
    private val updateNotificationState: UpdateNotificationState,
    private val updateAccountInfo: UpdateAccountInfo,
    private val getLoginMethod: GetLoginMethod,
    private val updateLoginMethod: UpdateLoginMethod,
    private val signOut: SignOut
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState : StateFlow<ProfileState> get() = _profileState

    init {
        fetchAccountInfo()
    }

    private fun fetchAccountInfo() {
        viewModelScope.launch {
            try {
                val accountInfo = getAccountInfo()
                val settingsState = getSettingsState()
                _profileState.value = ProfileState(profileData = ProfileData(accountInfo, settingsState))
            } catch (e : Exception) {
                _profileState.value = ProfileState(error = e.stackTraceToString())
            }
        }
    }

    fun updateProfile(newUsername : String, newPassword : String, onSuccess : (String) -> Unit, onFailure : (String) -> Unit) {
        viewModelScope.launch {
            val result = updateAccountInfo(newUsername, newPassword)
            result.onSuccess {
                onSuccess("Update Successfully")
                updateProfileState()
            }.onFailure {t ->
                onFailure(t.localizedMessage.orEmpty())
            }
        }
    }

    private fun updateProfileState() {
        viewModelScope.launch {
            val accountInfo = getAccountInfo()
            val newProfileData = _profileState.value.profileData.copy(accountInfo = accountInfo)
            _profileState.value = ProfileState(profileData = newProfileData)
        }
    }

    fun updateSettingsState() {
        val settingsState = getSettingsState()
        val newProfileData = _profileState.value.profileData.copy(settingsState = settingsState)
        _profileState.value = ProfileState(profileData = newProfileData)
    }

    fun updateThemeStatus(theme : String) = updateThemeState(theme)

    fun updateNotificationStatus() = updateNotificationState()

    fun getLoginMethod() = getLoginMethod.invoke()

    fun logOut() {
        signOut()
        updateLoginMethod(null)
    }

}