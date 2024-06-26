package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class SaveAccountInfo @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(accountInfo: AccountInfo) {
        firebaseRepository.saveAccountInfo(accountInfo)
    }
}