package com.psyluckco.sqwads.core.model

import java.time.LocalDateTime

data class Message(
    val id: String = "",
    val text: String = "",
    val sentBy: String = "",
    val fromCurrentUser: Boolean = false,
    val sentAt: LocalDateTime = LocalDateTime.now()
)
