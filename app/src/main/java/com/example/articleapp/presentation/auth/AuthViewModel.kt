package com.example.articleapp.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.use_case.auth.LoginUser
import com.example.articleapp.domain.use_case.auth.RegisterUser
import com.example.articleapp.domain.use_case.auth.SaveAccountInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val loginUser: LoginUser,
    private val saveAccountInfo: SaveAccountInfo
) : ViewModel() {

    private val _authState = mutableStateOf(AuthState())
    val authState : State<AuthState> get() = _authState

    fun login(email : String, password : String) {
        viewModelScope.launch {
            try {
                loginUser(email, password)
                _authState.value = AuthState(isSuccess = true)
            } catch (e : Exception) {
                _authState.value = AuthState(error = e.localizedMessage)
            }
        }
    }

    fun register(username : String, imageUri : String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val authResult = registerUser(email, password)
                authResult.user?.let {user ->
                    val accountInfo = AccountInfo(user.uid, username, imageUri, email)
                    saveAccountInfo(accountInfo)
                }
                _authState.value = AuthState(isSuccess = true)
            } catch (e : Exception) {
                _authState.value = AuthState(error = e.localizedMessage)
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState()
    }

    fun getAvatarPhotos() : List<String> {
        val baseUrl = "https://raw.githubusercontent.com/arif-cloud/DatasetProfilePhotos/main/"
        return listOf(
            baseUrl+"avatar_1.png",
            baseUrl+"avatar_2.png",
            baseUrl+"avatar_3.png",
            baseUrl+"avatar_4.png",
            baseUrl+"avatar_5.png",
            baseUrl+"avatar_6.png",
            baseUrl+"avatar_7.png",
            baseUrl+"avatar_8.png"
        )
    }

}