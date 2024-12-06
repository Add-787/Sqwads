package com.psyluckco.sqwads.core.data.repository

interface RoomRepository {

    suspend fun getRoom(roomId: String) : Result<Unit>

}