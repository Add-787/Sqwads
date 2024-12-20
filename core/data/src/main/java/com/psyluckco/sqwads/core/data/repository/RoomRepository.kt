package com.psyluckco.sqwads.core.data.repository

import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    suspend fun getRoom(roomId: String) : Flow<Room>
    suspend fun createNewRoom(roomName: String) : Result<String>
    suspend fun getAllOpenRooms() : Flow<List<Room>>
    suspend fun joinRoom(roomId: String)  : Result<Response<Unit>>
    suspend fun leaveRoom(roomId: String) : Result<Response<Unit>>
}