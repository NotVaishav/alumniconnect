package com.example.alumniconnect.ui.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.alumniconnect.data.DomainRepository
import com.example.alumniconnect.data.User
import com.example.alumniconnect.data.UserDataStore
import com.example.alumniconnect.data.UsersRepository
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SignupViewModel(
    private val usersRepository: UsersRepository,
    private val userDataStore: UserDataStore,
    private val domainRepository: DomainRepository
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
        Log.d("CHECKING", _uiState.value.isStudent.toString())
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
        setNotFoundStatus()
        _uiState.update { currentState -> currentState.copy(emailId = value) }
    }

    fun setPassword(value: String) {
        setNotFoundStatus()
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
        viewModelScope.launch {
            val uiState = _uiState.value
            domainRepository.getAllDomains().collect { domainList ->
                val randomDomain = domainList.random()

                usersRepository.getUserByEmailStream(uiState.emailId).collect { user ->
                    if (user != null) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                existingUserFound = true
                            )
                        }
                    } else {
                        val newUser = User(
                            profilePic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/no_image_profile.jpg",
                            backGroundPic = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/background_drop.jpg",
                            id = uiState.id,
                            firstName = uiState.firstName,
                            lastName = uiState.lastName,
                            emailId = uiState.emailId,
                            password = uiState.password,
                            role = if (uiState.isStudent) "Student" else "Alumni",
                            isStudent = uiState.isStudent,
                            about = "Set your about section!",
                            domainId = randomDomain.id,
                            isFeatured = true,
                            resume = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/resume_template.pdf",
                            coverLetter = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/cl_template.pdf",
                            instagramId = "@test.instagram",
                            linkedInId = "@test.linkedin",
                            facebookId = "@test.facebook",
                            contactNumber = "(123)-456-7899",
                            coverLetterHits = Random.nextInt(50, 100),
                            profileHits = Random.nextInt(100, 400),
                            resumeHits = Random.nextInt(50, 100)
                        )
                        usersRepository.insertUser(newUser)
                        _uiState.update { currentState ->
                            currentState.copy(
                                areCredentialsValid = true
                            )
                        }
                    }
                }
            }
            saveUserLoggedInInformation()
        }

    }

    fun setNotFoundStatus() {
        _uiState.update { currentState ->
            currentState.copy(
                passwordIncorrect = false,
                userNotFound = false,
                existingUserFound = false
            )
        }
    }

    fun validateCredentials() {
        viewModelScope.launch {
            val uiState = _uiState.value
            usersRepository.getUserByEmailStream(uiState.emailId).collect { user ->
                if (user == null) {
                    // User not found
                    Log.d("USER", "User not found")
                    // Provide feedback for user not found
                    _uiState.update { currentState ->
                        currentState.copy(
                            userNotFound = true
                        )
                    }
                } else {
                    // User found, compare passwords
                    if (user.password == uiState.password) {
                        // Password correct
                        Log.d("USER", "Password correct")
                        _uiState.update { currentState ->
                            currentState.copy(
                                areCredentialsValid = true
                            )
                        }
                        // Provide feedback for correct password
                    } else {
                        // Password incorrect
                        Log.d("USER", "Incorrect password")
                        // Provide feedback for incorrect password
                        _uiState.update { currentState ->
                            currentState.copy(
                                passwordIncorrect = true
                            )
                        }
                    }
                }
            }

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
    var userNotFound: Boolean = false,
    var passwordIncorrect: Boolean = false,
    var existingUserFound: Boolean = false

)