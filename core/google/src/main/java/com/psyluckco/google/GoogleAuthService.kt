package com.psyluckco.google

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface GoogleAuthService {
    suspend fun googleSignIn(context: Context) : FirebaseUser?
}