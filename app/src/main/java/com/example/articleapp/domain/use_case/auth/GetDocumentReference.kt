package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class GetDocumentReference @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke() : DocumentReference {
        val result = firebaseRepository.getAccountInfo(firebaseRepository.getUserId())
        return result.documents[0].reference
    }
}