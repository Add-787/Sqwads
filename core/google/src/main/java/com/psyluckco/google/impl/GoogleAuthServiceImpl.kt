/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.google.impl

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.psyluckco.google.GoogleAuthService
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class GoogleAuthServiceImpl @Inject constructor(): GoogleAuthService{

    override suspend fun googleSignIn(context: Context) {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("932977409718-1smktmhecn3m039o1of2vrftash1acoh.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .setNonce("")
        .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        val credentialManager = CredentialManager.create(context)
        coroutineScope {
            try {
                val result = credentialManager.getCredential(request= request, context = context)
                println(result)
//                handleSignIn(result)
            }catch (e: GetCredentialException){
                println(e.message)
            }

        }


    }
}