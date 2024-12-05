/**
 * Created by developer on 05-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Exceptions
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseUserIsNullException
import com.psyluckco.sqwads.core.model.firebase.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val accountService: AccountService
) : UserRepository {
    override suspend fun saveUser(user: User) {
        userColRef.document(user.id).set(user).await()
    }

    override suspend fun isUserInDatabase(email: String): Result<Boolean> = runCatching {
        val querySnapshot = userColRef.whereEqualTo("email", email).get().await()
        querySnapshot.size() > 0
    }

    override suspend fun getUser(): User = accountService.userId?.let {
            userId ->
            getUserDocRef(userId).get().await().toObject(User::class.java)
        } ?: throw FirebaseUserIsNullException()

    override suspend fun getUserRef(): DocumentReference {
        return accountService.userId?.let {
            getUserDocRef(it)
        } ?: throw FirebaseUserIsNullException()
    }

    override fun getUserFlow(): Flow<User?> {
        return accountService.userId?.let {
            userId -> getUserDocRef(userId).dataObjects<User>()
        } ?: throw FirebaseUserIsNullException()
    }

    private val userColRef by lazy { firestore.collection(USERS) }
    private fun getUserDocRef(id: String) = userColRef.document(id)


    companion object {
        private const val USERS = "users"
    }
}