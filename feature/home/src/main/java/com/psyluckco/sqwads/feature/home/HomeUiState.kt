package com.psyluckco.sqwads.feature.home

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room
import com.psyluckco.sqwads.core.model.firebase.FirebaseUser

data class HomeUiState(
    val loadingState : LoadingState = LoadingState.Idle,
    val isDialogOpened : Boolean = false,
    val rooms: List<Room> = emptyList(),
    val userName: String = "",
)

sealed class HomeEvent {
    data object OnProfileClicked : HomeEvent()
    data object OnUserStatsClicked: HomeEvent()
    data class OnRoomCreated(val roomId : String) : HomeEvent()
    data class OnRoomJoining(val roomId: String) : HomeEvent()
    data class OnLoadingStateChanged(val state: LoadingState) : HomeEvent()
    data class OnRoomNameProvided(val roomName: String) : HomeEvent()
    data object OnEditRoomNameDialogOpened : HomeEvent()
    data object OnEditRoomNameDialogClosed : HomeEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
    data class NavigateToRoom(val roomId : String) : NavigationState
    data object NavigateToProfile : NavigationState
    data object NavigateToStats : NavigationState
}
