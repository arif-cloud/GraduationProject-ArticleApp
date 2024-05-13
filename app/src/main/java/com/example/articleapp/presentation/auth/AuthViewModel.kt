package com.example.articleapp.presentation.auth

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.common.Constants
import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.use_case.auth.GoogleSignIn
import com.example.articleapp.domain.use_case.auth.LoginUser
import com.example.articleapp.domain.use_case.auth.RegisterUser
import com.example.articleapp.domain.use_case.auth.SaveAccountInfo
import com.example.articleapp.domain.use_case.auth.UpdateLoginMethod
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUser: RegisterUser,
    private val loginUser: LoginUser,
    private val googleSignIn: GoogleSignIn,
    private val saveAccountInfo: SaveAccountInfo,
    private val updateLoginMethod: UpdateLoginMethod
) : ViewModel() {

    private val _authState = mutableStateOf(AuthState())
    val authState : State<AuthState> get() = _authState

    fun login(email : String, password : String) {
        viewModelScope.launch {
            try {
                loginUser(email, password)
                _authState.value = AuthState(isSuccess = true)
                updateLoginMethod("email_password")
            } catch (e : Exception) {
                _authState.value = AuthState(error = e.localizedMessage)
            }
        }
    }

    fun register(username : String, imageUri : Uri?, email: String, password: String) {
        viewModelScope.launch {
            try {
                val authResult = registerUser(email, password)
                authResult.user?.run {
                    val accountInfo = AccountInfo(uid, username, imageUri.toString(), email)
                    saveAccountInfo(accountInfo)
                }
                _authState.value = AuthState(isSuccess = true)
            } catch (e : Exception) {
                _authState.value = AuthState(error = e.localizedMessage)
            }
        }
    }

    fun googleSignIn(intent : Intent, oneTapClient : SignInClient) = viewModelScope.launch {
        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            googleSignIn.invoke(googleCredentials)
            _authState.value = AuthState(isSuccess = true)
            updateLoginMethod("google")
        } catch (e : Exception) {
            _authState.value = AuthState(error = e.localizedMessage)
        }
    }

    fun getIntentSender(oneTapClient : SignInClient, onResult : (IntentSender?) -> Unit) = viewModelScope.launch {
        try {
            val result = oneTapClient.beginSignIn( buildSignInRequest() ).await()
            onResult(result.pendingIntent.intentSender)
        } catch (e : Exception) {
            _authState.value = AuthState(error = e.localizedMessage)
        }
    }

    private fun buildSignInRequest() : BeginSignInRequest {
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(Constants.WEB_CLIENT_ID)
                .build()
        ).setAutoSelectEnabled(true).build()
    }

    fun resetState() {
        _authState.value = AuthState()
    }

}