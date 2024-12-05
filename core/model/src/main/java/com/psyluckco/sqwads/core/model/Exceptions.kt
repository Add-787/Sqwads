package com.psyluckco.sqwads.core.model

object Exceptions {
    class EmailVerificationNotDoneException : Exception()
    class UserDoesNotExistException : Exception()
    class FirebaseUserIsNullException : Exception()
}