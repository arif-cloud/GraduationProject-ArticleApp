package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class UpdatePassword @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(newPassword : String) : Task<Void> {
        return firebaseRepository.updatePassword(newPassword)
    }
}