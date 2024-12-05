package com.psyluckco.sqwads.core.model.firebase

import com.google.firebase.firestore.DocumentId

data class FirebaseUser(
    @DocumentId val id: String,
    val name: String,
    val email: String
)
