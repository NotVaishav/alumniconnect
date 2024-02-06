package com.example.alumniconnect.ui.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    fun validateForm() {
        val uiState = uiState
        if (uiState.value.firstName != "" && uiState.value.lastName != ""
            && uiState.value.school != "" && uiState.value.program != ""
        ) {
            _uiState.update { currentState -> currentState.copy(isFormValid = true) }
        } else {
            _uiState.update { currentState -> currentState.copy(isFormValid = false) }
        }
    }

    fun onDone(navController: NavController) {
        validateForm()
        if (uiState.value.isFormValid) {
            navController.navigate(
                AlumniConnectNavDestinations.Credentials.title
            )
        }
    }
}

data class SignupUiState(
    var isStudent: Boolean = true,
    var firstName: String = "",
    var lastName: String = "",
    var school: String = "",
    var program: String = "",
    var emailId: String = "",
    var password: String = "",
    var graduationYear: Int? = null,
    var coopYear: Int? = null,
    var isFormValid: Boolean = false
)