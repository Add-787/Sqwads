/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.home

import com.psyluckco.firebase.AccountService
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val roomRepository: RoomRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(HomeUiState(userName = if(accountService.displayName.isNullOrEmpty()) "Guest" else accountService.displayName!!))
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.OnProfileClicked -> { }
            is HomeEvent.OnRoomClicked -> { }
            HomeEvent.OnNewRoomCreated -> TODO()
        }
    }
}