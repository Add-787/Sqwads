package com.psyluckco.sqwads.core.model

data class Room(
    val name: String,
    val members: List<String>,
    val createdAt: Long = System.currentTimeMillis()
)