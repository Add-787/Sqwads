package com.psyluckco.sqwads.feature.home

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room

data class HomeUiState(
    val displayName : String = "Unknown",
    val isLoading : LoadingState = LoadingState.Idle,
    val rooms: List<Room> = emptyList()
)

sealed class HomeUiEvent {
    data object OnProfileClicked : HomeUiEvent()
    data class OnSquadClicked(val squadId : String) : HomeUiEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
    data class NavigateToRoom(val squadId : String) : NavigationState
    data object NavigateToProfile : NavigationState
}
