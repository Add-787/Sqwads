package com.psyluckco.firebase

import com.google.firebase.firestore.DocumentReference
import com.psyluckco.sqwads.core.model.firebase.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUser(user: User)
    suspend fun isUserInDatabase(email: String): Result<Boolean>
    suspend fun getUser(): User
    suspend fun getUserRef() : DocumentReference
    fun getUserFlow(): Flow<User?>

}