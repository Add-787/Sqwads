package com.psyluckco.sqwads.core.data.repository

import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    suspend fun getRoom(roomId: String) : Result<Flow<Room>>
    suspend fun createNewRoom(roomName: String) : Result<String>

}