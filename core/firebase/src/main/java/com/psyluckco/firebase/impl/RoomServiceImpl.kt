/**
 * Created by developer on 03-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.firebase.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.psyluckco.firebase.CreateRoomResponse
import com.psyluckco.firebase.GetOpenRoomsResponse
import com.psyluckco.firebase.RoomService
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.firebase.Room
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class RoomServiceImpl @Inject constructor(
    private val db : FirebaseFirestore,
    private val auth: FirebaseAuth,
) : RoomService{

    override suspend fun loadAllOpenRooms(): Flow<GetOpenRoomsResponse> = callbackFlow {

        lateinit var registration : ListenerRegistration

        try {

            val query = db.collection("rooms")
                .whereEqualTo("isOpened", true)

            registration = query.addSnapshotListener { result, e ->
                    if(e != null) {
                        trySend(Response.Failure(e))
                        return@addSnapshotListener
                    }

                    val rooms = ArrayList<Room>();
                    for(doc in result!!) {
                        val openRoom = doc.toObject(Room::class.java)

                        rooms.add(openRoom)
                    }

                    trySend(Response.Success(rooms))
                }

        } catch(e: Exception) {
            trySend(Response.Failure(e))
            awaitClose { registration.remove() }
        }
    }

    override suspend fun createNewRoom(roomName: String): CreateRoomResponse = suspendCancellableCoroutine {
        continuation ->

        val userId = auth.currentUser?.uid ?: ""

        val userRef = db.collection("users").document(userId)

        val createdRoom = Room(
            name = roomName,
            createdBy = userRef,
            createdAt = FieldValue.serverTimestamp(),
            members = listOf(userRef),
            isOpened = true
        )

        db.collection("rooms")
            .add(createdRoom)
            .addOnSuccessListener {
                continuation.resume(Response.Success(true))
            }
            .addOnFailureListener { e ->
                continuation.resume(Response.Failure(e))
            }
    }
}