package com.psyluckco.firebase

import com.google.android.play.integrity.internal.m
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import com.psyluckco.sqwads.core.model.firebase.FirebaseMessage
import com.psyluckco.sqwads.core.model.firebase.FirebaseRoom
import kotlinx.coroutines.flow.Flow

typealias GetOpenRoomsResponse = Response<List<FirebaseRoom>>
typealias CreateRoomResponse = Result<String>
typealias SendMessageResponse = Result<String>
typealias JoinRoomResponse = Response<Unit>
typealias LeaveRoomResponse = Response<Unit>
typealias RecommendedRoomsResponse = Response<List<Room>>

interface RoomService {

    suspend fun loadAllOpenRooms() : Flow<List<FirebaseRoom>>
    suspend fun loadRoomData(roomId: String) : Flow<FirebaseRoom?>
    suspend fun createNewRoom(roomName: String) : CreateRoomResponse
    suspend fun joinRoom(roomId: String) : JoinRoomResponse
    suspend fun leaveRoom(roomId: String) : LeaveRoomResponse
    suspend fun sendMessageInRoom(roomId: String, text: String) : SendMessageResponse
    suspend fun loadAllMessagesInRoom(roomId: String) : Flow<List<FirebaseMessage>>

}