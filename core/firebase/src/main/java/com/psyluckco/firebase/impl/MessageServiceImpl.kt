/**
 * Created by developer on 06-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.psyluckco.firebase.GetAllMessagesResponse
import com.psyluckco.firebase.MessageService
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Message
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.FirebaseMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(
    private val roomService: RoomService,
) : MessageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun loadAllMessages(): GetAllMessagesResponse {

        return roomService.loadAllOpenRooms().flatMapConcat {
            rooms -> rooms.asFlow().flatMapMerge { room -> roomService.loadAllMessagesInRoom(room.id) }
        }.scan(emptyList()) { accumulated, newList -> accumulated + newList }

    }
}