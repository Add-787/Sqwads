package com.psyluckco.sqwads.feature.profile

import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService
): BaseViewModel(logService){
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)


}