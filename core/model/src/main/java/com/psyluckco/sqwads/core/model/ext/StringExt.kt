/**
 * Created by developer on 18-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.model.ext

import android.util.Patterns
import java.util.regex.Pattern

private const val MinPassPattern = 8
private const val PassPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
private const val NamePassPattern = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$"

fun String.isValidEmail() : Boolean = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidName() : Boolean {
    return this.isNotBlank() && this.length > 2 && Pattern.compile(NamePassPattern).matcher(this).matches()
}

fun String.isValidPassword(): Boolean = isNotBlank() &&
            length >= MinPassPattern &&
            Pattern.compile(PassPattern).matcher(this).matches()

fun String.passwordMatches(repeated: String) : Boolean {
    return this == repeated
}