package com.psyluckco.sqwads.core.model

import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime

data class Room(
    @DocumentId val id: String,
    val name: String,
    val members: List<String>,
    val createdAt: LocalDateTime,
    val createdBy: String?
)
