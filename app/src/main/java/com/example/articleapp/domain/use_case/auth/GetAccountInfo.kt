package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.firebase.firestore.toObject
import javax.inject.Inject

class GetAccountInfo @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke() : AccountInfo {
        val response = firebaseRepository.getAccountInfo(firebaseRepository.getUserId())
        val accountInfo = response.documents[0].toObject<AccountInfo>()
        return accountInfo ?: AccountInfo()
    }
}