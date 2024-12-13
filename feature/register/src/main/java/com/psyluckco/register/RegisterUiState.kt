package com.psyluckco.register

import com.psyluckco.sqwads.core.model.LoadingState

data class RegisterUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val loadingState: LoadingState = LoadingState.Idle
)

sealed class RegisterEvent {
    data class OnUsernameChanged(val userName : String) : RegisterEvent()
    data class OnEmailChanged(val email: String) : RegisterEvent()
    data class OnPasswordChanged(val password: String) : RegisterEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()
    data class OnLoadingStateChanged(val state: LoadingState) : RegisterEvent()
    data object OnRegisterClicked : RegisterEvent()
    data object OnAlreadyHaveAccountClick : RegisterEvent()
}

sealed class NavigationState {
    data object None: NavigationState()
    data class NavigateToHome(val userId: String) : NavigationState()
    data object NavigateToLogin : NavigationState()
}
