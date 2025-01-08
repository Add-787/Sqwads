package com.psyluckco.sqwads.core.model

object Exceptions {
    class EmailVerificationNotDoneException : Exception()
    class UserDoesNotExistException : Exception()
    class RoomCouldNotBeLoadedException : Exception()
    class FirebaseRoomsCouldNotBeLoadedException : Exception()
    class FirebaseUserIsNullException : Exception()
    class FirebaseRoomCouldNotBeCreatedException : Exception()
    class FirebaseMessageCouldNotBeSentException: Exception()
    class FirebaseRoomCouldNotBeFoundException : Exception()
}