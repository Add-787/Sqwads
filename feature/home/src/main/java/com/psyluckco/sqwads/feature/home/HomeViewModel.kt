/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.home

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.di.Dispatcher
import com.psyluckco.sqwads.core.model.di.SqwadsDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {

        if(initializeCalled) {
            return
        }
        initializeCalled = true

        viewModelScope.launch {
            launch { getAllOpenRooms() }
        }
    }



    fun onEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.OnProfileClicked -> { _navigationState.update { NavigationState.NavigateToProfile } }
            is HomeEvent.OnRoomCreated -> { _navigationState.update { NavigationState.NavigateToRoom(event.roomId) } }
            is HomeEvent.OnRoomNameProvided -> { onNewRoomCreated(event.roomName) }
            is HomeEvent.OnLoadingStateChanged -> _uiState.update { it.copy(loadingState = event.state) }
            HomeEvent.OnEditRoomNameDialogOpened -> _uiState.update { it.copy(isDialogOpened = true) }
            HomeEvent.OnEditRoomNameDialogClosed -> _uiState.update { it.copy(isDialogOpened = false) }
            is HomeEvent.OnRoomJoining -> { _navigationState.update { NavigationState.NavigateToRoom(event.roomId) } }
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
                onEvent(HomeEvent.OnEditRoomNameDialogClosed)
                delay(500)
                onEvent(HomeEvent.OnRoomCreated(id))
            }.onFailure {
                it.message?.let { message -> SnackbarManager.showMessage(message) }
                onEvent(HomeEvent.OnLoadingStateChanged(LoadingState.Idle))
            }
    }

    private fun getAllOpenRooms() = launchCatching {
        roomRepository.getAllOpenRooms()
            .catch {
                println(it)
            }
            .collectLatest { rooms -> _uiState.update { it.copy(rooms = rooms) } }
    }
}