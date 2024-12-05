package com.psyluckco.sqwads.core.model

import java.time.LocalDateTime

data class Room(
    val name: String,
    val members: List<String>,
    val createdAt: LocalDateTime
)
