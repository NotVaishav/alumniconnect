package com.example.alumniconnect.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alumniconnect.R
import com.example.alumniconnect.data.DomainRepository
import com.example.alumniconnect.data.EducationItemRepository
import com.example.alumniconnect.data.ExperienceItemRepository
import com.example.alumniconnect.data.User
import com.example.alumniconnect.data.UsersRepository
import com.example.alumniconnect.ui.screens.profile.EducationItem
import com.example.alumniconnect.ui.screens.profile.ExperienceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val usersRepository: UsersRepository,
    private val domainRepository: DomainRepository,
    private val educationRepository: EducationItemRepository,
    private val experienceRepository: ExperienceItemRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getAlumniList()
        getDomainsList()
    }

    fun onSearchTextChange(text: String) {
        val uiStateList = uiState.value.currentList
        val alumniList = uiStateList.filter { user ->
            user.firstName?.contains(text, ignoreCase = true) ?: false
        }
        _uiState.update { currentState ->
            currentState.copy(searchText = text, currentList = alumniList)
        }
    }

    fun onToggleSearch() {
        getAlumniList()
        val currentIsSearching = uiState.value.isSearching
        _uiState.update { currentState ->
            currentState.copy(
                isSearching = !currentIsSearching
            )

        }
        if (currentIsSearching) {
            onSearchTextChange("")
        }
    }

    fun getAlumniCount(domainId: Int): Int {
        val testList = _uiState.value.currentList
        return testList.count { it.domainId == domainId }
    }

    fun getAlumniList() {
        viewModelScope.launch {
            usersRepository.getAllUsersStream().collect { userList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        currentList = userList
                    )
                }
            }
        }
    }

    fun getDomainsList() {
        viewModelScope.launch {
            domainRepository.getAllDomains().collect { domainList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        domainsList = domainList
                    )
                }
            }
        }
    }

    fun getDomainInfo(domainId: Int) {
        val domainList = _uiState.value.domainsList
        val domain = domainList.find { it.id == domainId }
        if (domain != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentDomain = domain.name
                )
            }
        }
    }

    fun getUserEducation(userId: Int) {
        Log.d("CHECKING EDUCATION", userId.toString())
        viewModelScope.launch {
            educationRepository.getEducationForUser(userId).collect { educationList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userEducation = educationList
                    )
                }
            }
        }
    }

    fun getUserExperience(userId: Int) {
        viewModelScope.launch {
            experienceRepository.getExperienceForUser(userId).collect { experienceList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userExperience = experienceList
                    )
                }
            }
        }
    }
}


data class HomeUiState(
    var isSearching: Boolean = false,
    var searchText: String = "",
    var currentDomain: String = "",
    var currentUserIndex: Int = 0,
    var currentList: List<User> = listOf(),
    var domainsList: List<com.example.alumniconnect.data.Domain> = listOf(),
    var userExperience: List<com.example.alumniconnect.data.ExperienceItem> = listOf(),
    var userEducation: List<com.example.alumniconnect.data.EducationItem> = listOf(

    ),
)