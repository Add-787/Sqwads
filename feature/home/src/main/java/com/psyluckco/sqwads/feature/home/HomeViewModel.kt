/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.home

import androidx.compose.material3.Snackbar
import com.psyluckco.firebase.AccountService
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val roomRepository: RoomRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.OnProfileClicked -> { }
            is HomeEvent.OnRoomClicked -> { _navigationState.update { NavigationState.NavigateToRoom(event.roomId) } }
            is HomeEvent.OnNewRoomClicked -> { onNewRoomCreated(event.roomName) }
            is HomeEvent.OnLoadingStateChanged -> _uiState.update { it.copy(loadingState = event.state) }
        }
    }

    fun resetNavigation() {
        _navigationState.update { NavigationState.None }
    }

    private fun onNewRoomCreated(name: String) = launchCatching {

        onEvent(HomeEvent.OnLoadingStateChanged(LoadingState.Loading))

        roomRepository.createNewRoom(name)
            .onSuccess {
                id ->
                onEvent(HomeEvent.OnLoadingStateChanged(LoadingState.Idle))
                delay(500)
                _navigationState.update { NavigationState.NavigateToRoom(id) }
            }.onFailure {
                it.message?.let { message -> SnackbarManager.showMessage(message) }
                onEvent(HomeEvent.OnLoadingStateChanged(LoadingState.Idle))
            }
    }
}