/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.psyluckco.firebase.LanguageService
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.snackbar.SnackbarManager
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Message
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
    private val languageService: LanguageService,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(JoinedRoomUiState())
    val uiState = _uiState.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

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
            launch { getAllRoomMessages(id) }
        }
    }

    fun onMessageChange(newMessage: String) {
        _message.update { newMessage }
    }

    fun onEvent(event: JoinedRoomEvent) {
        when(event) {
            is JoinedRoomEvent.LeaveRoomClicked -> { onLeaveRoomClicked(roomId =  event.roomId) }
            is JoinedRoomEvent.OnLoadingStateChanged -> _uiState.update { it.copy(loadingState = event.state) }
            is JoinedRoomEvent.OnMessageSent -> { onMessageSent(id = event.roomId, message = event.message) }
            is JoinedRoomEvent.OnTranslateMessageClicked -> { onTranslateMessage(event.message)}
        }
    }

    private fun onTranslateMessage(message: Message) = launchCatching {
        val text = languageService.convertToDeviceLanguage(message.text)
        _uiState.update {
            val messages = it.messages.map {
                msg ->
                if(msg.id == message.id) msg.copy(text = text)
                else msg
            }
            it.copy(messages = messages)
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

    private fun onLeaveRoomClicked(roomId: String) = launchCatching {
        roomRepository.leaveRoom(roomId).onSuccess {
            _navigationState.update { NavigationState.NavigateToHome }
        }.onFailure {
            //TODO: display message
        }
    }

    private suspend fun getAllRoomMessages(
        id: String
    ) {
        roomRepository.loadAllMessages(id).collectLatest {
            messages -> _uiState.update { it.copy(messages = messages) }
        }
    }

    private fun onMessageSent(
        id: String,
        message: String
    ) = launchCatching {
        roomRepository.sendMessage(id,message).onSuccess {
            onMessageChange("")
        }.onFailure {
            e -> e.message?.let { SnackbarManager.showMessage(it) }
        }

    }

    override fun onCleared() {
        super.onCleared()

    }

    fun resetNavigation() {
        _navigationState.update { NavigationState.None }
    }
}