package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsername @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val getLoginMethod: GetLoginMethod
) {
    suspend operator fun invoke() : String = withContext(Dispatchers.IO) {
        if (getLoginMethod() == "google")
            firebaseRepository.getCurrentUser()?.displayName
        else {
            val response = firebaseRepository.getAccountInfo(firebaseRepository.getUserId())
            val username = response.documents[0].getString("username")
            username
        } ?: ""
    }
}