package com.psyluckco.sqwads.core.model.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp

data class FirebaseRoom(
    val name: String,
    val createdBy: DocumentReference,
    @ServerTimestamp
    val createdAt: FieldValue,
    val members: List<DocumentReference>,
    val isOpened: Boolean
)
