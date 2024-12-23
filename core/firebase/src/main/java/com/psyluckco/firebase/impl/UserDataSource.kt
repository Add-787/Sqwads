/**
 * Created by developer on 05-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseUserIsNullException
import com.psyluckco.sqwads.core.model.firebase.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth,
) : UserRepository {

    override suspend fun saveUser(id: String, username: String, email: String) {

//        val newUser = FirebaseUser(
//            id = id,
//            name = username,
//            email = email
//        )

        val userData = mapOf(
            "name" to username,
            "email" to email
        )

        userColRef.document(id).set(userData).await()
    }

    override suspend fun isUserInDatabase(email: String): Result<Boolean> = runCatching {
        val querySnapshot = userColRef.whereEqualTo("email", email).get().await()
        querySnapshot.size() > 0
    }

    override suspend fun getLoggedInUser(): FirebaseUser = auth.currentUser?.uid?.let {
            userId ->
            getUserDocRef(userId).get().await().toObject(FirebaseUser::class.java)
        } ?: throw FirebaseUserIsNullException()

    override suspend fun getUserRef(): DocumentReference {
        return auth.currentUser?.uid?.let {
            getUserDocRef(it)
        } ?: throw FirebaseUserIsNullException()
    }

    override fun getLoggedInUserFlow(): Flow<FirebaseUser?> {
        return auth.currentUser?.uid?.let {
            userId -> getUserDocRef(userId).dataObjects<FirebaseUser>()
        } ?: throw FirebaseUserIsNullException()
    }

    override suspend fun getUserInfo(id: String): FirebaseUser {
        return getUserDocRef(id).get().await().toObject(FirebaseUser:: class.java) ?: throw FirebaseUserIsNullException()
    }

    private val userColRef by lazy { firestore.collection(USERS) }
    private fun getUserDocRef(id: String) = userColRef.document(id)


    companion object {
        private const val USERS = "users"
    }
}