/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.data.repository.MessageRepository
import com.psyluckco.sqwads.core.data.repository.RoomRepository
import com.psyluckco.sqwads.core.data.repository.UserRepository
import com.psyluckco.sqwads.core.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val roomRepository: RoomRepository,
    logService: LogService
): BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(StatsUiState(loadingState = LoadingState.Loading))
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

            roomRepository.getRecommendedRooms().combine(messageRepository.loadAllMessagesOfUser()) {
                rooms,messages -> Pair(rooms,messages)
            }.collectLatest {
                (rooms,messages) ->
                if(rooms.isNotEmpty()) {
                    _uiState.update { it.copy(recommendedRooms = rooms.take(3), loadingState = LoadingState.Idle) }
                }

                if(messages.isNotEmpty()) {
                    val scores = messages.map { m -> String.format("%.2f", m.score).toDouble() }
                    _uiState.update { it.copy(sentimentScores = scores, loadingState = LoadingState.Idle) }
                }

            }

        }

    }

}