package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

class GoogleSignIn @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(credential: AuthCredential) {
        firebaseRepository.googleSignIn(credential)
    }
}