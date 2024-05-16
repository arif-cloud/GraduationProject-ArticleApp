package com.example.articleapp.domain.use_case.auth

import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateAccountInfo @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(newUsername : String, newPassword : String): Result<String> {
        return try {
            val taskList = mutableListOf<Task<Void>>()
            if (newUsername.isNotEmpty())
                taskList.add(firebaseRepository.updateUsername(newUsername))
            taskList.add(firebaseRepository.updatePassword(newPassword))
            Tasks.whenAllComplete(taskList).await()
            Result.success("Update Successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}