package com.example.alumniconnect.ui.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun updateUserState(isStudent: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isStudent = isStudent
            )
        }
    }

    fun setFirstName(value: String) {
        _uiState.update { currentState -> currentState.copy(firstName = value) }
        validateForm()
    }

    fun setLastName(value: String) {
        _uiState.update { currentState -> currentState.copy(lastName = value) }
        validateForm()
    }

    fun setSchool(value: String) {
        _uiState.update { currentState -> currentState.copy(school = value) }
        validateForm()
    }

    fun setProgram(value: String) {
        _uiState.update { currentState -> currentState.copy(program = value) }
        validateForm()
    }

    fun setEmail(value: String) {
        _uiState.update { currentState -> currentState.copy(emailId = value) }
    }

    fun setPassword(value: String) {
        _uiState.update { currentState -> currentState.copy(password = value) }
    }

    private fun validateForm() {
        val uiState = uiState
        if (uiState.value.firstName != "" && uiState.value.lastName != ""
            && uiState.value.school != "" && uiState.value.program != ""
        ) {
            _uiState.update { currentState -> currentState.copy(isFormValid = true) }
        } else {
            _uiState.update { currentState -> currentState.copy(isFormValid = false) }
        }
    }

    fun onDone(navController: NavController, viaNextBtn: Boolean = false) {
        viewModelScope.launch {
            validateForm()
            if (!viaNextBtn) {
                delay(500)
            }
            if (uiState.value.isFormValid) {
                navController.navigate(
                    AlumniConnectNavDestinations.Credentials.title
                )
            }
        }
    }

    fun validateCredentials() {
        viewModelScope.launch {
            delay(3000)
            _uiState.update { currentState -> currentState.copy(areCredentialsValid = true) }
        }
    }
}

data class SignupUiState(
    var isStudent: Boolean = true,
    var firstName: String = "Vaishav",
    var lastName: String = "Dhepe",
    var school: String = "STFX",
    var program: String = "MACS",
    var emailId: String = "",
    var password: String = "",
    var graduationYear: Int? = null,
    var coopYear: Int? = null,
    var isFormValid: Boolean = true,
    var areCredentialsValid: Boolean = false
)