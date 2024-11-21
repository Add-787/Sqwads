/**
 * Created by developer on 18-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.register

import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.AuthenticationRepository
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.ext.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.psyluckco.sqwads.core.design.R.string as AppText

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    private val userName
        get() = uiState.value.userName

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEvent(event : RegisterEvent) {
        when(event) {
            RegisterEvent.OnAlreadyHaveAccountClick -> {
                _navigationState.update { NavigationState.NavigateToLogin }
            }
            is RegisterEvent.OnConfirmPasswordChanged -> {
                _uiState.update{ it.copy(confirmPassword = event.confirmPassword) }
            }
            is RegisterEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is RegisterEvent.OnLoadingStateChanged -> {
                _uiState.update{ it.copy(loadingState = event.state) }
            }
            is RegisterEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
            RegisterEvent.OnRegisterClicked -> onRegisterClick()
            is RegisterEvent.OnUsernameChanged -> {
                _uiState.update { it.copy(userName = event.userName) }
            }
        }
    }

    fun resetNavigation() {
        _navigationState.update { NavigationState.None }
    }

    private fun onRegisterClick() = launchCatching {

        if(!formValidation()) return@launchCatching



        authenticationRepository.signUpWithEmailAndPassword(email.trim(), password, userName)
            .onSuccess {

            }
            .onFailure {
                it.message?.let { error -> SnackbarManager.showMessage(error) }
            }

        onEvent(RegisterEvent.OnLoadingStateChanged(LoadingState.Idle))

    }

    private fun formValidation() : Boolean {
        if(!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return false
        } else if(password.isBlank()) {
            SnackbarManager.showMessage(AppText.placeholder)
            return false
        }
        else return true
    }
}