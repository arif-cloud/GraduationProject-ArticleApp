package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(email: String, password: String) : AuthResult {
        return firebaseRepository.loginUser(email, password)
    }
}