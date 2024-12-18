package com.psyluckco.sqwads.feature.profile

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.firebase.FirebaseUser

data class ProfileUiState(
    val loadingState: LoadingState = LoadingState.Idle,
    val isDialogOpened: Boolean = false,
    val user: FirebaseUser = FirebaseUser()
)

sealed class ProfileEvent{
    data class OnLoadingStateChanged(val state: LoadingState) : ProfileEvent()
    data object OnLogoutClicked : ProfileEvent()
    data object OnBackButtonClicked: ProfileEvent()
}

sealed interface NavigationState {
    data object None : NavigationState
    data object NavigateToHome : NavigationState
}