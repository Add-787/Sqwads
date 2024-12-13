package com.psyluckco.google

import android.content.Context

interface GoogleAuthService {
    suspend fun googleSignIn(context: Context)
}