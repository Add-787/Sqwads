/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.repository.impl

import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.UserRepository
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

            val isPresent = userRepository.isUserInDatabase(email).getOrThrow()

            if(!isPresent) {
                val firebaseUser = FirebaseUser(
                    id = result.user!!.uid,
                    email = result.user!!.email ?: "",
                    name = result.user!!.displayName ?: ""
                )

                userRepository.saveUser(firebaseUser)
            }

            return@runCatchingWithContext result.user?.uid.toString()
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
}