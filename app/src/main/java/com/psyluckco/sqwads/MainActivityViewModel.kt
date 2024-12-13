/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psyluckco.firebase.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class AccountState {
    data object Loading : AccountState()
    data class UserAlreadySignIn(val userId: String) : AccountState()
    data object UserNotSignIn : AccountState()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _accountState = MutableStateFlow<AccountState>(AccountState.Loading)
    val accountState = _accountState.asStateFlow()

    private var initializeCalled = false
    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch {
            runCatching {
                if(accountService.isEmailVerified) {
                    accountService.firebaseUser?.getIdToken(true)?.await()?.token?.let {
                        _accountState.update { AccountState.UserAlreadySignIn(accountService.userId ?: "") }
                    }
                } else {
                    _accountState.update { AccountState.UserNotSignIn }
                }
            }.onFailure {
                _accountState.update { AccountState.UserNotSignIn }
            }
        }

    }


}