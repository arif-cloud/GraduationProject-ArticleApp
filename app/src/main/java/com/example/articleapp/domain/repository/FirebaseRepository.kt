package com.example.articleapp.domain.repository

import com.example.articleapp.domain.model.AccountInfo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot


interface FirebaseRepository {
    suspend fun loginUser(email : String, password : String) : AuthResult
    suspend fun registerUser(email : String, password : String) : AuthResult
    suspend fun saveAccountInfo(accountInfo: AccountInfo) : DocumentReference
    suspend fun getAccountInfo(userId : String) : QuerySnapshot
    fun updatePassword(newPassword : String) : Task<Void>
    fun getUserId() : String
    fun signOut()
}