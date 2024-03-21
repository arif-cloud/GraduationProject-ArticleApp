package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class UpdateUsername @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(documentReference: DocumentReference, newUsername : String) : Task<Void> {
        return documentReference.update("username", newUsername)
    }
}