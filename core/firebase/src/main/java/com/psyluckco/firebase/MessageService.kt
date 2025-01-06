package com.psyluckco.firebase

import com.psyluckco.sqwads.core.model.firebase.FirebaseMessage
import kotlinx.coroutines.flow.Flow

typealias GetAllMessagesResponse = Flow<List<FirebaseMessage>>

interface MessageService {

    suspend fun loadAllMessages() : GetAllMessagesResponse

}