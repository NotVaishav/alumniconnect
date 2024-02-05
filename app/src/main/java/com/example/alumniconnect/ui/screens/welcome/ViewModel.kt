package com.example.alumniconnect.ui.screens.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WelcomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun updateUserState(isExistingUser: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isExistingUser = isExistingUser
            )
        }

    }
}

data class WelcomeUiState(
    var isExistingUser: Boolean = true
)