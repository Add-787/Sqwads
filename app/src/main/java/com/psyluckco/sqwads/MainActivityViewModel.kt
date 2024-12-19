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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
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
    val accountState = _accountState.onStart { getAccountStatus() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AccountState.Loading
        )

    private fun getAccountStatus() {
        viewModelScope.launch {
            accountService.getAccountStatus()
                .catch {
                    println(it)
                    _accountState.update { AccountState.UserNotSignIn }
                }
                .collectLatest {
                    if(it.currentUser == null){
                        _accountState.update { AccountState.UserNotSignIn }
                    }else{
                        if(it.currentUser!!.isEmailVerified) {
                            accountService.firebaseUser?.getIdToken(true)?.await()?.token?.let {
                                _accountState.update { AccountState.UserAlreadySignIn(accountService.userId ?: "") }
                            }
                        } else {
                            _accountState.update { AccountState.UserNotSignIn }
                        }
                    }
                }
        }
    }

}