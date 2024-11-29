package com.psyluckco.sqwads.feature.home

import com.psyluckco.sqwads.core.model.LoadingState

data class HomeUiState(
    val displayName : String = "Unknown",
    val isLoading : LoadingState = LoadingState.Idle
)

sealed class HomeUiEvent {
    data object OnProfileClicked : HomeUiEvent()
    data class OnSquadClicked(val squadId : String) : HomeUiEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
    data class NavigateToSquad(val squadId : String) : NavigationState
    data object NavigateToProfile : NavigationState
}
