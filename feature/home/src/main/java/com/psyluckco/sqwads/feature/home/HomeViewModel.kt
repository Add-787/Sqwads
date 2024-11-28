/**
 * Created by developer on 08-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.feature.home

import com.psyluckco.firebase.AccountService
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : BaseViewModel(logService) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    init {

    }

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            HomeUiEvent.OnProfileClicked -> TODO()
            is HomeUiEvent.OnSquadClicked -> TODO()
        }
    }
}