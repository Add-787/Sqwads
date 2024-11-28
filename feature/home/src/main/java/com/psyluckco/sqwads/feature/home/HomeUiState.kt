package com.psyluckco.sqwads.feature.home

data class HomeUiState(
    val userName: String = "Unknown",
    val isLoading : Boolean = false
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
