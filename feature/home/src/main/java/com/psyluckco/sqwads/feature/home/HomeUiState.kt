package com.psyluckco.sqwads.feature.home

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room

data class HomeUiState(
    val userName : String = "Guest",
    val isLoading : LoadingState = LoadingState.Idle,
    val rooms: List<Room> = emptyList()
)

sealed class HomeEvent {
    data object OnProfileClicked : HomeEvent()
    data class OnRoomClicked(val roomId : String) : HomeEvent()
    data object OnNewRoomCreated : HomeEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
    data class NavigateToRoom(val squadId : String) : NavigationState
    data object NavigateToProfile : NavigationState
}
