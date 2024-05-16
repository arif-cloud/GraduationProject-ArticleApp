package com.example.articleapp.data.firebase.implementation

import android.net.Uri
import com.example.articleapp.domain.model.AccountInfo
import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore : FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) : FirebaseRepository {

    override suspend fun loginUser(email: String, password: String): AuthResult = withContext(ioDispatcher) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun registerUser(email: String, password: String): AuthResult = withContext(ioDispatcher) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun googleSignIn(credential: AuthCredential): AuthResult = withContext(ioDispatcher) {
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun saveAccountInfo(accountInfo: AccountInfo): Unit = withContext(ioDispatcher) {
        firebaseStorage.reference.child(accountInfo.userId).putFile(Uri.parse(accountInfo.imageUri)).addOnSuccessListener { task ->
            task.metadata?.reference?.downloadUrl?.addOnSuccessListener {url ->
                val newAccountInfo = accountInfo.copy(imageUri = url.toString())
                firebaseFirestore.collection("users").add(newAccountInfo)
            }
        }
    }

    override suspend fun getRegisteredUserAccountInfo(): AccountInfo = withContext(ioDispatcher) {
        val response = firebaseFirestore.collection("users").whereEqualTo("userId", firebaseAuth.currentUser?.uid.orEmpty()).get().await()
        response.documents[0].toObject<AccountInfo>() ?: AccountInfo()
    }

    override suspend fun getGoogleAccountInfo(): AccountInfo = withContext(ioDispatcher) {
        firebaseAuth.currentUser?.run {
            AccountInfo(
                userId = uid,
                username = displayName,
                email = email,
                imageUri = photoUrl.toString()
            )
        } ?: AccountInfo()
    }

    override suspend fun updateUsername(newUsername: String): Task<Void> = withContext(ioDispatcher) {
        val result = firebaseFirestore.collection("users").whereEqualTo("userId", firebaseAuth.currentUser?.uid).get().await()
        val documentReference = result.documents[0].reference
        documentReference.update("username", newUsername)
    }

    override fun updatePassword(newPassword: String): Task<Void> {
        return firebaseAuth.currentUser!!.updatePassword(newPassword)
    }
    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    override fun getUserId(): String = firebaseAuth.currentUser?.uid ?: ""
    override fun signOut() = firebaseAuth.signOut()

}