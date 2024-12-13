package com.psyluckco.firebase

import com.google.firebase.firestore.DocumentReference
import com.psyluckco.sqwads.core.model.firebase.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUser(id: String, username: String, email: String)
    suspend fun isUserInDatabase(email: String): Result<Boolean>
    suspend fun getUser(): FirebaseUser
    suspend fun getUserRef() : DocumentReference
    fun getUserFlow(): Flow<FirebaseUser?>

}