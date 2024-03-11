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
                Log.d("UPDATED VALUE", _uiState.value.userEducation.toString())
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


data class UserProfile(
    val id: Int,
    val profilePic: Int,
    val backGroundPic: Int,
    val firstName: String,
    val lastName: String,
    val role: String,
    val about: String,
    val isStudent: Boolean = false,
    val domain: String? = null,
    val contactNumber: String? = null,
    val isFeatured: Boolean = true,
    val educationInformation: List<EducationItem> = listOf(),
    val experienceInformation: List<ExperienceItem> = listOf(),
    val resume: Int? = null,
    val coverLetter: Int? = null,
    val instagramId: String? = null,
    val linkedInId: String? = null,
    val facebookId: String? = null
)

data class Domain(val name: String, val image: Int)

val userList = listOf(
    UserProfile(
        id = 1,
        profilePic = R.drawable.profile_pic,
        backGroundPic = R.drawable.profile_pic,
        firstName = "John",
        lastName = "Doe",
        role = "Software Developer",
        about = "I'm a positive person. I love to travel and eat. Always available to chat",
        domain = "IT",
        contactNumber = "(902)-318-6993",
        isFeatured = true,
        educationInformation = listOf(
            EducationItem(
                "St. Francis Xavier University",
                "Master's degree, Applied Computer Science",
                "Sep 2022",
                endDate = "May 2024"
            )
        ),
        experienceInformation = listOf(
            ExperienceItem(
                "Software Development Intern",
                "Dash Hudson",
                "May 2023",
                endDate = "Dec 2024",
                isCoop = true,
            ),
            ExperienceItem(
                "Python Developer",
                "Aptagrim",
                "Aug 2021",
                endDate = "Aug 2022",
            )
        ),
        resume = R.raw.madmidterm1,
        coverLetter = R.raw.madmidterm1,
        instagramId = "@vaishav",
        linkedInId = "@vaishavdhepe",
        facebookId = "@notvaishav"
    )
)

val domainsList = listOf(
    Domain("Engineering", R.drawable.profile_pic),
    Domain("Design", R.drawable.login_img),
    Domain("Product Management", R.drawable.profile_pic),
    Domain("Data Science", R.drawable.profile_pic),
    Domain("Marketing", R.drawable.profile_pic),
    Domain("Sales", R.drawable.profile_pic),
    Domain("Finance", R.drawable.profile_pic),
    Domain("Human Resources", R.drawable.login_img),
    Domain("Legal", R.drawable.profile_pic),
    Domain("IT", R.drawable.profile_pic),
)

data class HomeUiState(
    var isSearching: Boolean = false,
    var searchText: String = "",
//    var currentList: List<UserProfile> = userList,
    var currentDomain: String = "",
    var currentUserIndex: Int = 0,
    var currentList: List<User> = listOf(),
    var domainsList: List<com.example.alumniconnect.data.Domain> = listOf(),
    var userExperience: List<com.example.alumniconnect.data.ExperienceItem> = listOf(),
    var userEducation: List<com.example.alumniconnect.data.EducationItem> = listOf(
        com.example.alumniconnect.data.EducationItem(
            id = 1,
            userId = 16,
            school = "Test",
            degree = "qweqwe",
            startDate = "123",
            endDate = "123"
        )
    ),
)