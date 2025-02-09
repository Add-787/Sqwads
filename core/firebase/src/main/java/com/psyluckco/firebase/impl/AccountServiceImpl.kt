/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.ReloadUserResponse
import com.psyluckco.firebase.SendEmailVerificationResponse
import com.psyluckco.firebase.SendPasswordResetEmailResponse
import com.psyluckco.firebase.UpdatePasswordResponse
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Exceptions
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseUserIsNullException
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.FirebaseRoom
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val auth : FirebaseAuth,
) : AccountService {

    override val userId: String?
        get() = auth.currentUser?.uid

    override val displayName: String?
        get() = auth.currentUser?.displayName

    override val isEmailVerified: Boolean
        get() = auth.currentUser?.isEmailVerified == true

    override val email: String?
        get() = auth.currentUser?.email

    override val firebaseUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String,
        password: String,
        displayName: String
    ): AuthResult {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()

        if(authResult.user != null) {
            userRepository.saveUser(
                id = authResult.user!!.uid,
                username = displayName,
                email = email
            )

        }

        return authResult
    }

    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        try {
            auth.currentUser?.sendEmailVerification()?.await()
            return Response.Success(data = true)
        } catch(e : Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult {

        return auth.signInWithEmailAndPassword(email,password).await()
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        try {
            auth.currentUser?.reload()?.await()
            return Response.Success(data = true)
        } catch(e : Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun updatePassword(password: String): UpdatePasswordResponse {
        try {
            auth.currentUser?.updatePassword(password)?.await()
            return Response.Success(data = true)
        } catch(e : Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        try {
            auth.sendPasswordResetEmail(email).await()
            return Response.Success(data = true)
        } catch(e: Exception) {
            return Response.Failure(e)
        }
    }

    override fun signOut() {
        return auth.signOut()
    }

    override suspend fun revokeAccess(): Result<Unit> = runCatching {
        auth.currentUser?.delete()?.await()
    }

    override suspend fun getAccountStatus() : Flow<FirebaseAuth> = callbackFlow{
        auth.addAuthStateListener {
            firebaseAuth -> trySend(firebaseAuth)
        }
        awaitClose()
    }
}