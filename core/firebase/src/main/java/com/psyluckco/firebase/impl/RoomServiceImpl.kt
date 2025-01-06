/**
 * Created by developer on 03-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import android.util.Log
import com.google.android.play.integrity.internal.m
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ListenSource
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SnapshotListenOptions
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.psyluckco.firebase.CreateRoomResponse
import com.psyluckco.firebase.JoinRoomResponse
import com.psyluckco.firebase.LeaveRoomResponse
import com.psyluckco.firebase.RecommendedRoomsResponse
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.SendMessageResponse
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.model.Exceptions
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseRoomCouldNotBeFoundException
import com.psyluckco.sqwads.core.model.Exceptions.FirebaseRoomsCouldNotBeLoadedException
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.Room
import com.psyluckco.sqwads.core.model.firebase.FirebaseMessage
import com.psyluckco.sqwads.core.model.firebase.FirebaseRoom
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.ZoneId
import javax.inject.Inject

class RoomServiceImpl @Inject constructor(
    private val firestore : FirebaseFirestore,
    private val userRepository: UserRepository,
) : RoomService{

    override suspend fun loadAllOpenRooms(): Flow<List<FirebaseRoom>> = callbackFlow {

        val openedRoomsQuery = roomsColRef
            .whereEqualTo("isOpened", true)

        openedRoomsQuery
            .addSnapshotListener() { values, error ->

            if(error != null) {
                return@addSnapshotListener
            }

            if(values != null && !values.isEmpty) {
                val rooms = values.toObjects(FirebaseRoom::class.java)
                trySend(rooms)
            } else {
                trySend(emptyList())
            }
        }

        awaitClose()
    }

    override suspend fun loadRecommendedRooms(): RecommendedRoomsResponse = callbackFlow {

        trySend(Response.Loading)

        val recommendedRoomsQuery = roomsColRef
            .whereEqualTo("isOpened", true)
            .orderBy("score", Query.Direction.DESCENDING)

        recommendedRoomsQuery.addSnapshotListener {
            values, error ->
                if(error != null) {
                    trySend(Response.Failure(FirebaseRoomsCouldNotBeLoadedException()))
                    return@addSnapshotListener
                }

                if(values != null && !values.isEmpty) {
                    val rooms = values.toObjects(FirebaseRoom::class.java)
                    trySend(Response.Success(rooms.toList()))
                } else {
                    trySend(Response.Success(emptyList()))
                }
        }

        awaitClose()

    }

    override suspend fun loadRoomData(roomId: String): Flow<FirebaseRoom?> = callbackFlow {
        
        val joinedRoomRef = roomsColRef.document(roomId)
        
        joinedRoomRef.addSnapshotListener { value, error ->
            if(error != null) {
                return@addSnapshotListener
            }

            if(value != null && value.exists()) {

                val room = value.toObject<FirebaseRoom>() ?: throw FirebaseRoomCouldNotBeFoundException()

                trySend(room)
            } else {
                trySend(null)
            }
        }
        awaitClose()
    }

    override suspend fun createNewRoom(roomName: String): CreateRoomResponse = runCatching {

        val user = userRepository.getUserRef()

        val roomData = mapOf(
            "name" to roomName,
            "createdBy" to user,
            "createdAt" to FieldValue.serverTimestamp(),
            "members" to listOf(user),
            "score" to 0.0,
            "isOpened" to true
        )

        roomsColRef.add(roomData).await().id
    }

    override suspend fun joinRoom(roomId: String): JoinRoomResponse {
        return try {
            roomsColRef
                .document(roomId)
                .update("members", FieldValue.arrayUnion(userRepository.getUserRef()))

            Response.Success(data = Unit)
        } catch (e: Exception) {
            Response.Failure(e)
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

    override suspend fun sendMessageInRoom(roomId: String, text: String): SendMessageResponse = runCatching {

        val user = userRepository.getUserRef()

        val messageData = mapOf(
            "sentAt" to FieldValue.serverTimestamp(),
            "sentBy" to user,
            "text" to text
        )

        roomsColRef.document(roomId).collection(MESSAGES).add(messageData).await().id

    }

    override suspend fun loadAllMessagesInRoom(roomId: String): Flow<List<FirebaseMessage>> = callbackFlow {

        val messagesQuery = roomsColRef.document(roomId).collection(MESSAGES)

        messagesQuery.addSnapshotListener { value, error ->
            if(error != null) {
                return@addSnapshotListener
            }

            if(value != null && !value.isEmpty) {
                val messages = value.toObjects(FirebaseMessage::class.java)
                trySend(messages)
            } else {
                trySend(emptyList())
            }
        }
        awaitClose()
    }

    private val roomsColRef by lazy { firestore.collection(ROOMS) }

    companion object {
        private const val ROOMS = "rooms"
        private const val MESSAGES = "messages"
    }
}