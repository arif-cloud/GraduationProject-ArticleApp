package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    operator fun invoke() {
        firebaseRepository.signOut()
    }
}