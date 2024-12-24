/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Message

data class JoinedRoomUiState(
    val roomName: String = "",
    val members: List<String> = emptyList(),
    val messages: List<Message> = emptyList(),
    val loadingState: LoadingState = LoadingState.Idle
)

sealed class JoinedRoomEvent {
    data class OnLoadingStateChanged(val state: LoadingState) : JoinedRoomEvent()
    data class LeaveRoomClicked(val roomId:String) : JoinedRoomEvent()
    data class OnMessageSent(val roomId: String, val message: String) : JoinedRoomEvent()
    data class OnTranslateMessageClicked(val message: Message) : JoinedRoomEvent()

}

sealed interface NavigationState {
    data object None : NavigationState
    data object NavigateToHome : NavigationState
}