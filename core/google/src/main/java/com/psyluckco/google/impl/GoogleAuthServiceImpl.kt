/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.google.impl

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.psyluckco.google.GoogleAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GoogleAuthServiceImpl @Inject constructor(): GoogleAuthService{
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var clientId = "932977409718-1smktmhecn3m039o1of2vrftash1acoh.apps.googleusercontent.com"
    private var db = Firebase.firestore

    override suspend fun googleSignIn(context: Context) : FirebaseUser? {
        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(serverClientId = clientId)
            .setNonce("sqwads")
        .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
        val credentialManager = CredentialManager.create(context)
        return  try {
            val result = withContext(Dispatchers.IO) {
                credentialManager.getCredential(request = request, context = context)
            }
            // Handle Sign-In result and return FirebaseUser
            handleSignIn(result)
            }catch (e: GetCredentialException){
                println(e.message)
                null
            }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse) : FirebaseUser? {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        return firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        println("Received an invalid google id token response")
                    }
                } else {
                   println("Unexpected type of credential")
                }
            }
            else -> {
                println("Unexpected type of credential")
            }
        }
        return null
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) : FirebaseUser? {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await().user
        }catch (e: Exception){
            println(e)
            null
        }
    }
}