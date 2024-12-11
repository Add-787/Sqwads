package com.psyluckco.sqwads.core.data.repository.impl

import com.psyluckco.firebase.RoomService
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.data.util.runCatchingWithContext
import com.psyluckco.sqwads.core.model.Exceptions
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseRoomCouldNotBeCreatedException
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseUserIsNullException
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import com.psyluckco.sqwads.core.model.di.Dispatcher
import com.psyluckco.sqwads.core.model.di.SqwadsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomService: RoomService,
    @Dispatcher(SqwadsDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : RoomRepository {
    override suspend fun getRoom(roomId: String): Result<Flow<Room>> = runCatching {
        flow {
            roomService.loadRoomData(roomId).collect {
                if(it == null) {
                    return@collect
                }

                emit(it.toRoom())
            }
        }
    }

    override suspend fun createNewRoom(roomName: String): Result<String> = runCatching {
        roomService.createNewRoom(roomName).getOrNull() ?: throw FirebaseRoomCouldNotBeCreatedException()
    }

    override suspend fun getAllOpenRooms(): Flow<List<Room>>  {
        return roomService.loadAllOpenRooms().map {
            rooms -> rooms.map { r -> r.toRoom() }
        }
    }

}