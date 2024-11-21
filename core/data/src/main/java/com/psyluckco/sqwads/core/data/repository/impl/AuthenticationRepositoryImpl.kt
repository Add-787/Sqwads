/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.repository.impl

import com.psyluckco.firebase.AccountService
import com.psyluckco.sqwads.core.data.repository.AuthenticationRepository
import com.psyluckco.sqwads.core.data.util.runCatchingWithContext
import com.psyluckco.sqwads.core.model.di.Dispatcher
import com.psyluckco.sqwads.core.model.di.SqwadsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val accountService : AccountService,
    @Dispatcher(SqwadsDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : AuthenticationRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String> =
        runCatchingWithContext(ioDispatcher) {
            val result = accountService.firebaseSignInWithEmailAndPassword(email, password)
            accountService.reloadFirebaseUser()
            return@runCatchingWithContext result.user?.uid.toString()
        }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        fullName: String
    ): Result<String> = runCatchingWithContext(ioDispatcher) {
        val result = accountService.firebaseSignUpWithEmailAndPassword(email, password)
        accountService.sendEmailVerification()
        return@runCatchingWithContext result.user?.uid.toString()
    }
}