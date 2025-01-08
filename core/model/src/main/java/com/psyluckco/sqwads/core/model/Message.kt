package com.psyluckco.sqwads.core.model

import java.time.LocalDateTime

data class Message(
    val id: String = "",
    val text: String = "",
    val sentBy: String = "",
    val score: Double = 0.0,
    val fromCurrentUser: Boolean = false,
    val sentAt: LocalDateTime? = null
)
