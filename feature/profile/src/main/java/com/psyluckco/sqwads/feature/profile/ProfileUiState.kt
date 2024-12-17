package com.psyluckco.sqwads.feature.profile

import com.psyluckco.sqwads.core.model.LoadingState

data class ProfileUiState(
    val loadingState: LoadingState = LoadingState.Idle,
    val isDialogOpened: Boolean = false
)

sealed class ProfileEvent{
    data class OnLoadingStateChanged(val state: LoadingState) : ProfileEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
}