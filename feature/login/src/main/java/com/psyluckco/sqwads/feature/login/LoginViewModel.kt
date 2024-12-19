/**
 * Created by developer on 04-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.login

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.integrity.internal.i
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.AuthenticationRepository
import com.psyluckco.sqwads.core.model.Exceptions
import com.psyluckco.sqwads.core.model.Exceptions.EmailVerificationNotDoneException
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.ext.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.psyluckco.sqwads.core.design.R.string as Apptext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is LoginEvent.OnForgotPasswordClicked -> _navigationState.update { NavigationState.NavigateToForgotPassword }
            is LoginEvent.OnLoadingStateChanged -> _uiState.update { it.copy(loadingState = event.state) }
            is LoginEvent.OnLoginClicked -> { onLoginClick() }
            is LoginEvent.OnPasswordChanged -> _uiState.update { it.copy(password = event.password) }
            is LoginEvent.OnGoogleSignInClicked -> { onGoogleSignInClicked(event.context) }
            LoginEvent.OnRegisterClicked -> _navigationState.update { NavigationState.NavigateToRegister }
        }
    }

    fun resetNavigation() {
        _navigationState.update { NavigationState.None }
    }

    private fun formValidation() : Boolean {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(Apptext.email_error)
            return false
        } else if(password.isBlank()) {
            SnackbarManager.showMessage(Apptext.email_error)
            return false
        } else {
            return true
        }
    }

    private fun onLoginClick() = launchCatching {
        if(!formValidation()) return@launchCatching

        onEvent(LoginEvent.OnLoadingStateChanged(LoadingState.Loading))

        authenticationRepository.signInWithEmailAndPassword(email, password)
            .onSuccess { userName ->
                onEvent(LoginEvent.OnLoadingStateChanged(LoadingState.Idle))
                delay(500)
                _navigationState.update { NavigationState.NavigateToHome(userName) }
            }.onFailure {
                if (it is EmailVerificationNotDoneException) {
                    SnackbarManager.showMessage(Apptext.placeholder)
                } else {
                    it.message?.let { message -> SnackbarManager.showMessage(message) }
                }

                onEvent(LoginEvent.OnLoadingStateChanged(LoadingState.Idle))
            }
    }

    private fun onGoogleSignInClicked(context: Context) = launchCatching {
        onEvent(LoginEvent.OnLoadingStateChanged(LoadingState.Loading))
        authenticationRepository.signInWithGoogle(context).onSuccess { userName ->
            onEvent(LoginEvent.OnLoadingStateChanged(LoadingState.Idle))
            _navigationState.update { NavigationState.NavigateToHome(userName) }
        }.onFailure {
            println("AUth Failed - google sign in")
        }
    }
}