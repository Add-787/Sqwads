/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.repository

import android.content.Context

interface AuthenticationRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String>
    suspend fun signUpWithEmailAndPassword(email: String, password: String, displayName: String): Result<String>
    suspend fun signInWithGoogle(context: Context): Result<String>
}