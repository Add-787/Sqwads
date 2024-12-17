/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import com.psyluckco.sqwads.core.model.LoadingState

data class JoinedRoomUiState(
    val roomName: String = "",
    val members: List<String> = emptyList(),
    val loadingState: LoadingState = LoadingState.Idle
)

sealed class JoinedRoomEvent {
    data class OnLoadingStateChanged(val state: LoadingState) : JoinedRoomEvent()
    data object LeaveRoomClicked : JoinedRoomEvent()
}