package com.psyluckco.sqwads.core.model

import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime

data class Room(
    val id: String,
    val name: String,
    val members: List<String> = emptyList(),
    val createdAt: LocalDateTime,
    val createdBy: String?,
    val score: Double = 0.0
)
