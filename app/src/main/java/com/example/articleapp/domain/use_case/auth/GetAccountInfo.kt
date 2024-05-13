package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetAccountInfo @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val getLoginMethod: GetLoginMethod
) {
    suspend operator fun invoke() : AccountInfo {
        return if (getLoginMethod() == "google")
            firebaseRepository.getGoogleAccountInfo()
        else
            firebaseRepository.getRegisteredUserAccountInfo()
    }
}