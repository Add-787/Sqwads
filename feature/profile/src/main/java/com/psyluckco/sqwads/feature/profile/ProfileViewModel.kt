package com.psyluckco.sqwads.feature.profile

import androidx.lifecycle.viewModelScope
import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.sqwads.core.common.BaseViewModel
import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.model.LoadingState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService : AccountService,
    logService: LogService
): BaseViewModel(logService){

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.onStart { getProfileData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ProfileUiState(
                loadingState = LoadingState.Loading,
            )
        )

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
    val navigationState = _navigationState.asStateFlow()

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.OnLoadingStateChanged -> { _uiState.update { it.copy(loadingState = event.state) }}
            is ProfileEvent.OnLogoutClicked -> { logout() }
            is ProfileEvent.OnBackButtonClicked -> { _navigationState.update { NavigationState.NavigateToHome }}
        }
    }

    private fun getProfileData() = launchCatching {
        try {
            onEvent(ProfileEvent.OnLoadingStateChanged(LoadingState.Loading))
            val user = userRepository.getLoggedInUser()
            _uiState.update { it.copy(user = user) }
            onEvent(ProfileEvent.OnLoadingStateChanged(LoadingState.Loading))
        }catch (e: Exception){
            println(e.message)
        }

    }

    private fun logout(){
        accountService.signOut()
    }

    fun resetNavigation() {
        _navigationState.update { NavigationState.None }
    }
}