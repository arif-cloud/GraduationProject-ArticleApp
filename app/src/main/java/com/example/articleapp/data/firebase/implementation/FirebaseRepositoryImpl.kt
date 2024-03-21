package com.example.articleapp.data.firebase.implementation

import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore : FirebaseFirestore,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : FirebaseRepository {

    override suspend fun loginUser(email: String, password: String): AuthResult = withContext(ioDispatcher) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun registerUser(email: String, password: String): AuthResult = withContext(ioDispatcher) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun saveAccountInfo(accountInfo: AccountInfo): DocumentReference = withContext(ioDispatcher) {
        firebaseFirestore.collection("users").add(accountInfo).await()
    }

    override suspend fun getAccountInfo(userId: String): QuerySnapshot = withContext(ioDispatcher) {
        firebaseFirestore.collection("users").whereEqualTo("userId", userId).get().await()
    }

    override fun updatePassword(newPassword: String): Task<Void> {
        return firebaseAuth.currentUser!!.updatePassword(newPassword)
    }

    override fun getUserId(): String = firebaseAuth.currentUser?.uid ?: ""

    override fun signOut() = firebaseAuth.signOut()

}