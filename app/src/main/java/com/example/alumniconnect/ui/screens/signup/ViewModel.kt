package com.example.alumniconnect.ui.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.alumniconnect.data.User
import com.example.alumniconnect.data.UserDataStore
import com.example.alumniconnect.data.UsersRepository
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val usersRepository: UsersRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())

    private val isLoggedInFlow = userDataStore.getUserStatus.map { isUserLoggedIn ->
        isUserLoggedIn
    }

    val uiState: StateFlow<SignupUiState> =
        combine(_uiState, isLoggedInFlow) { uiState, isLoggedIn ->
            uiState.copy(isUserLoggedIn = isLoggedIn)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value
        )

    private fun saveUserLoggedInInformation() {
        viewModelScope.launch { userDataStore.saveUserStatus(false) }
    }

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

    suspend fun saveUser() {
        _uiState.update { currentState -> currentState.copy(error = "") }
//        val result = usersRepository.insertUser(
//            User(
//                profilePic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/profile_pic.png",
//                backGroundPic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/profile_pic.png",
//                id = uiState.value.id,
//                emailId = uiState.value.emailId,
//                password = uiState.value.password,
//                role = "Student",
//                about = "This is my about section",
//                domainId = 2,
//                isFeatured = true,
//                resume = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/project-1.pdf",
//                coverLetter = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/project-1.pdf",
//                instagramId = "@test.instagram",
//                linkedInId = "@test.linkedin",
//                facebookId = "@test.facebook",
//                contactNumber = "(123)-456-7899"
//            )
//        )

        saveUserLoggedInInformation()
        _uiState.update { currentState -> currentState.copy(error = "success") }
//        result.onSuccess {
//            saveUserLoggedInInformation()
//            _uiState.update { currentState -> currentState.copy(error = "success") }
//        }.onFailure { e ->
//            _uiState.update { currentState -> currentState.copy(error = "$e") }
//            Log.d("SQLLITE", "$e")
//        }

    }

    fun validateCredentials() {
        viewModelScope.launch {
            delay(3000)
            _uiState.update { currentState -> currentState.copy(areCredentialsValid = true) }
        }
    }
}

data class SignupUiState(
    var isUserLoggedIn: Boolean = false,
    val id: Int = 0,
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
    var areCredentialsValid: Boolean = false,
    var error: String = ""
)