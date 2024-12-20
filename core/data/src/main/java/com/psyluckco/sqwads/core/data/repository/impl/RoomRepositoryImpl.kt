package com.psyluckco.sqwads.core.data.repository.impl


import android.os.Build.VERSION_CODES.P
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseRoomCouldNotBeCreatedException
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import com.psyluckco.sqwads.core.model.di.Dispatcher
import com.psyluckco.sqwads.core.model.di.SqwadsDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomService: RoomService,
    private val userRepository: UserRepository,
    @Dispatcher(SqwadsDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : RoomRepository {
    override suspend fun getRoom(roomId: String): Flow<Room> {
        return roomService.loadRoomData(roomId).filterNotNull().map {
            val admin = it.createdBy?.id ?: ""
            val members = it.members.map { ref -> userRepository.getUserInfo(ref.id).name }
            
            return@map Room(
                id = it.id,
                name = it.name,
                createdAt = it.createdAt.toDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
                createdBy = userRepository.getUserInfo(admin).name,
                members = members
            )

        }
    }

    override suspend fun createNewRoom(roomName: String): Result<String> = runCatching {
        roomService.createNewRoom(roomName).getOrNull() ?: throw FirebaseRoomCouldNotBeCreatedException()
    }

    override suspend fun getAllOpenRooms(): Flow<List<Room>> {

        return roomService.loadAllOpenRooms().map {
            firebaseRooms -> firebaseRooms
            .filter { it.createdAt?.toDate() != null }
            .map { it.toRoom() }
        }.catch {
            Timber.i(it)
        }
    }

    override suspend fun joinRoom(roomId: String) : Result<Response<Unit>> = runCatching  {
        roomService.joinRoom(roomId)
    }

    override suspend fun leaveRoom(roomId: String) : Result<Response<Unit>> = runCatching {
        roomService.leaveRoom(roomId)
    }

}