package com.psyluckco.firebase

import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.FirebaseRoom
import kotlinx.coroutines.flow.Flow

typealias GetOpenRoomsResponse = Response<List<FirebaseRoom>>
typealias CreateRoomResponse = Result<String>
typealias JoinRoomResponse = Response<Unit>
typealias LeaveRoomResponse = Response<Unit>

interface RoomService {

    suspend fun loadAllOpenRooms() : Flow<List<FirebaseRoom>>
    suspend fun createNewRoom(roomName: String) : CreateRoomResponse
    suspend fun joinRoom(roomId: String) : JoinRoomResponse
    suspend fun leaveRoom(roomId: String) : LeaveRoomResponse

}