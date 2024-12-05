package com.psyluckco.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.psyluckco.sqwads.core.model.Response

typealias SendEmailVerificationResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias UpdatePasswordResponse = Response<Boolean>

interface AccountService {
    val userId : String?
    val displayName: String?
    val isEmailVerified: Boolean
    val email: String?
    val firebaseUser: FirebaseUser?
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String, displayName: String) : AuthResult
    suspend fun sendEmailVerification() : SendEmailVerificationResponse
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String) : AuthResult
    suspend fun reloadFirebaseUser() : ReloadUserResponse
    suspend fun updatePassword(password: String) : UpdatePasswordResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    fun signOut()
    suspend fun revokeAccess() : Result<Unit>
}