package com.psyluckco.firebase

import com.psyluckco.sqwads.core.model.Message
import com.psyluckco.sqwads.core.model.Response

typealias GetMessagesResponse = Response<List<Message>>
interface MessageService {

    suspend fun loadAllMessagesofUser(userId: String)
}