/**
 * Created by developer on 10-10-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.login

import com.psyluckco.sqwads.core.model.LoadingState

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loadingState: LoadingState = LoadingState.Idle
)

sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    data class OnLoadingStateChanged(val state: LoadingState) : LoginEvent()
    data object OnLoginClicked : LoginEvent()
    data object OnRegisterClicked : LoginEvent()
    data object OnForgotPasswordClicked : LoginEvent()
}

sealed class NavigationState {
    data object None : NavigationState()
    data class NavigateToHome(val userId: String) : NavigationState()
    data object NavigateToRegister : NavigationState()
    data object NavigateToForgotPassword : NavigationState()
}