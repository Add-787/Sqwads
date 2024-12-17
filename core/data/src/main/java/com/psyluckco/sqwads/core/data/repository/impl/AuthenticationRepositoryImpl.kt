/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.repository.impl

import android.content.Context
import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.google.GoogleAuthService
import com.psyluckco.sqwads.core.data.repository.AuthenticationRepository
import com.psyluckco.sqwads.core.data.util.runCatchingWithContext
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseUserIsNullException
import com.psyluckco.sqwads.core.model.di.Dispatcher
import com.psyluckco.sqwads.core.model.di.SqwadsDispatchers
import com.psyluckco.sqwads.core.model.firebase.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val accountService : AccountService,
    private val userRepository: UserRepository,
    private val googleAuthService: GoogleAuthService,
    @Dispatcher(SqwadsDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String> =
        runCatchingWithContext(ioDispatcher) {
            val result = accountService.firebaseSignInWithEmailAndPassword(email, password)
            accountService.reloadFirebaseUser()

            if(result.user == null)
            {
                throw FirebaseUserIsNullException()
            }

            result.user?.uid.toString()
        }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        displayName: String
    ): Result<String> = runCatchingWithContext(ioDispatcher) {
        val result = accountService.firebaseSignUpWithEmailAndPassword(email, password, displayName)
        accountService.sendEmailVerification()

        return@runCatchingWithContext result.user?.uid.toString()
    }

    override suspend fun signInWithGoogle(context: Context): Result<String> = runCatchingWithContext(ioDispatcher){
        val user = googleAuthService.googleSignIn(context)
        if(user != null){
            userRepository.saveUser(user.uid, user.displayName ?: "Unknown",user.email ?: "No email")
        }
        return@runCatchingWithContext user?.uid.toString()
    }
}