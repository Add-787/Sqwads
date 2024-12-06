/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

data class JoinedRoomUiState(
    val roomName: String = "",
    val members: List<String> = emptyList()
)

sealed class JoinedRoomEvent {
    data object LeaveRoomClicked : JoinedRoomEvent()
}