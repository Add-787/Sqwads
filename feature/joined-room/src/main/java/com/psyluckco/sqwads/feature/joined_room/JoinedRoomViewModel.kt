/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinedRoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(JoinedRoomUiState())
    val uiState = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize(
        id: String
    ) {

        if(initializeCalled) {
            return
        }
        initializeCalled = true

        viewModelScope.launch {
            launch { getRoomInfo(id) }
        }
    }

    fun onEvent(event: JoinedRoomEvent) {
        when(event) {
            JoinedRoomEvent.LeaveRoomClicked -> { }
            is JoinedRoomEvent.OnLoadingStateChanged -> _uiState.update { it.copy(loadingState = event.state) }
        }
    }

    private suspend fun getRoomInfo(
        id: String
    ) = launchCatching {
        onEvent(JoinedRoomEvent.OnLoadingStateChanged(state = LoadingState.Idle))
        roomRepository.getRoom(id).collectLatest {
            room -> _uiState.update { it.copy(roomName = room.name, members = room.members) }
        }

    }


    override fun onCleared() {
        super.onCleared()

    }
}