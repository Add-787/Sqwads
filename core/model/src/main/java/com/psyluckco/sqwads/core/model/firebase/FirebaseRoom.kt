package com.psyluckco.sqwads.core.model.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import com.psyluckco.sqwads.core.model.Room
import java.time.LocalDateTime
import java.time.ZoneId

data class FirebaseRoom(
    @DocumentId val id: String = "",
    val name: String = "",
    val createdBy: DocumentReference? = null,
    val createdAt: Timestamp = Timestamp.now(),
    val members: List<DocumentReference> = emptyList(),
    val isOpened: Boolean = true
) {

    fun toRoom() : Room {
        return Room(
            id = id,
            name = name,
            members = members.map { ref -> ref.id },
            createdAt = createdAt.toDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime(),
            createdBy = createdBy?.id,
        )
    }

}
