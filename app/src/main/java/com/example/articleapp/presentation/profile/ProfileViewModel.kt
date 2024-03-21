package com.example.articleapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.domain.use_case.auth.GetAccountInfo
import com.example.articleapp.domain.use_case.auth.GetDocumentReference
import com.example.articleapp.domain.use_case.settings.GetSettingsState
import com.example.articleapp.domain.use_case.auth.SignOut
import com.example.articleapp.domain.use_case.auth.UpdatePassword
import com.example.articleapp.domain.use_case.auth.UpdateUsername
import com.example.articleapp.domain.use_case.settings.UpdateNotificationState
import com.example.articleapp.domain.use_case.settings.UpdateThemeState
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountInfo: GetAccountInfo,
    private val getSettingsState: GetSettingsState,
    private val updateNotificationState: UpdateNotificationState,
    private val updateThemeState: UpdateThemeState,
    private val getDocumentReference: GetDocumentReference,
    private val updateUsername: UpdateUsername,
    private val updatePassword: UpdatePassword,
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
            val documentReference = getDocumentReference()
            Tasks.whenAllComplete(
                updateUsername(documentReference, newUsername),
                updatePassword(newPassword)
            ).addOnSuccessListener {
                onSuccess("Update Successfully")
                updateProfileState()
            }.addOnFailureListener {e ->
                onFailure(e.stackTraceToString())
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

    fun updateThemeStatus(theme : String) = updateThemeState(theme)

    fun updateNotificationStatus(isGranted : Boolean) = updateNotificationState(isGranted)

    fun logOut() = signOut()

}