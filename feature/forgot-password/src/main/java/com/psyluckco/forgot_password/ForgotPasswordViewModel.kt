package com.psyluckco.forgot_password

import androidx.compose.material3.Snackbar
import com.psyluckco.firebase.AccountService
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Response
import com.psyluckco.sqwads.core.model.ext.isValidEmail
import com.psyluckco.sqwads.feature.forgot_password.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.psyluckco.sqwads.core.design.R.string as AppText


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : BaseViewModel(logService) {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Idle)
    val loadingState = _loadingState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.update { newEmail }
    }

    private fun onLoadingStateChanged(newLoadingState: LoadingState) {
        _loadingState.update { newLoadingState }
    }

    fun sendPasswordResetEmail(clearAndNavigateLogin: () -> Unit) {
        if(!email.value.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            onLoadingStateChanged(LoadingState.Loading)

            val response = accountService.sendPasswordResetEmail(email.value.trim())
            if(response is Response.Success) {
                onLoadingStateChanged(LoadingState.Idle)
                delay(50L)
                clearAndNavigateLogin()
            } else if(response is Response.Failure) {
                SnackbarManager.showMessage(
                    response.e.message
                        ?: "Your password reset email could not be sent. Please try again."
                )
            }

            onLoadingStateChanged(LoadingState.Idle)
        }
    }

}