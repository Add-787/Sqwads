package com.psyluckco.firebase

import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.Room
import kotlinx.coroutines.flow.Flow

typealias GetOpenRoomsResponse = Response<List<Room>>
typealias CreateRoomResponse = Result<String>
typealias JoinRoomResponse = Response<Unit>
typealias LeaveRoomResponse = Response<Unit>

interface RoomService {

    suspend fun loadAllOpenRooms() : Flow<List<Room>>
    suspend fun createNewRoom(roomName: String) : CreateRoomResponse
    suspend fun joinRoom(roomId: String) : JoinRoomResponse
    suspend fun leaveRoom(roomId: String) : LeaveRoomResponse

}