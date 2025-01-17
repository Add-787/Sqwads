package com.psyluckco.sqwads.core.model.firebase

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.Timestamp

data class FirebaseMessage(
    @DocumentId val id: String = "",
    val score: Double = 0.0,
    val text: String = "",
    val sentBy: DocumentReference? = null,
    val sentAt: Timestamp? = Timestamp.now()
)
