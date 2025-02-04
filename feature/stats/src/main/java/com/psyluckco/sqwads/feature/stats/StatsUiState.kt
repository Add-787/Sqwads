/**
 * Created by developer on 01-01-2025.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.stats

import com.psyluckco.sqwads.core.model.LoadingState
import com.psyluckco.sqwads.core.model.Room

data class StatsUiState(
    val loadingState: LoadingState = LoadingState.Idle,
    val recommendedRooms: List<Room> = emptyList(),
    val sentimentScores: List<Double> = emptyList()
)

sealed class StatsEvent {
    data class OnLoadingStateChanged(val state: LoadingState) : StatsEvent()
}

sealed interface NavigationState {
    data object None: NavigationState
}