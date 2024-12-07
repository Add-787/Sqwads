/**
 * Created by developer on 06-12-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.joined_room

import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JoinedRoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(JoinedRoomUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: JoinedRoomEvent) {
        when(event) {
            JoinedRoomEvent.LeaveRoomClicked -> TODO()
        }
    }

    init {

    }

    private fun getRoomInfo() {

    }


    override fun onCleared() {
        super.onCleared()

    }
}