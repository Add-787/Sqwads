/**
 * Created by developer on 03-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.psyluckco.firebase.CreateRoomResponse
import com.psyluckco.firebase.JoinRoomResponse
import com.psyluckco.firebase.LeaveRoomResponse
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.FirebaseRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomServiceImpl @Inject constructor(
    private val firestore : FirebaseFirestore,
    private val userRepository: UserRepository,
) : RoomService{

    override suspend fun loadAllOpenRooms(): Flow<List<FirebaseRoom>> = callbackFlow {

        val openedRoomsQuery = roomsColRef
            .whereEqualTo("isOpened", true)

        openedRoomsQuery.addSnapshotListener { values, error ->
            if(error != null) {
                return@addSnapshotListener
            }

            if(values != null && !values.isEmpty) {
                trySend(values.toObjects<FirebaseRoom>())
            } else {
                trySend(emptyList())
            }
        }
    }

    override suspend fun createNewRoom(roomName: String): CreateRoomResponse = runCatching {

        val userRef = userRepository.getUserRef()

        val createdRoom = FirebaseRoom(
            name = roomName,
            createdBy = userRef,
            createdAt = FieldValue.serverTimestamp(),
            members = listOf(userRef),
            isOpened = true
        )

        roomsColRef.add(createdRoom).await().id
    }

    override suspend fun joinRoom(roomId: String): JoinRoomResponse {
        try {
            roomsColRef
                .document(roomId)
                .update("members", FieldValue.arrayUnion(userRepository.getUserRef()))

            return Response.Success(data = Unit)
        } catch (e: Exception) {
            return Response.Failure(e)
        }
    }

    override suspend fun leaveRoom(roomId: String): LeaveRoomResponse {
        try {
            roomsColRef
                .document(roomId)
                .update("members", FieldValue.arrayRemove(userRepository.getUserRef()))

            return Response.Success(data = Unit)
        } catch(e: Exception) {
            return Response.Failure(e)
        }
    }

    private val roomsColRef by lazy { firestore.collection(ROOMS) }

    companion object {
        private const val ROOMS = "rooms"
    }
}