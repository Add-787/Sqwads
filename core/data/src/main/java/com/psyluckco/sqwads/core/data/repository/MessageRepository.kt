package com.psyluckco.sqwads.core.data.repository

import com.psyluckco.sqwads.core.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun loadAllMessagesOfUser(): Flow<List<Message>>
}