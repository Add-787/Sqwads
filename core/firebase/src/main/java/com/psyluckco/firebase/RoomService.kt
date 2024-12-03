package com.psyluckco.firebase

import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import kotlinx.coroutines.flow.Flow

typealias GetOpenRoomsResponse = Response<List<Room>>
typealias CreateRoomResponse = Response<Boolean>

interface RoomService {

    suspend fun loadAllOpenRooms() : Flow<GetOpenRoomsResponse>
    suspend fun createNewRoom(roomName: String) : CreateRoomResponse


}