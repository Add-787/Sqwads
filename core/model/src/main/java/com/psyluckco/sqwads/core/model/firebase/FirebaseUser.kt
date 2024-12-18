package com.psyluckco.sqwads.core.model.firebase

import com.google.firebase.firestore.DocumentId

data class FirebaseUser(
    val name: String,
    val email: String
){
    constructor() : this("","")
}
