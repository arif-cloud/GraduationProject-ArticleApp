package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetGoogleAccountInfo @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke() : AccountInfo {
        return firebaseRepository.getCurrentUser()?.run {
            AccountInfo(
                userId = uid,
                username = displayName,
                email = email,
                imageUri = photoUrl.toString()
            )
        } ?: AccountInfo()
    }
}