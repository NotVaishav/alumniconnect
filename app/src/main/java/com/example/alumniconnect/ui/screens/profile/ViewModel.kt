package com.example.alumniconnect.ui.screens.profile

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alumniconnect.R
import com.example.alumniconnect.data.DomainRepository
import com.example.alumniconnect.data.EducationItem
import com.example.alumniconnect.data.EducationItemRepository
import com.example.alumniconnect.data.ExperienceItem
import com.example.alumniconnect.data.ExperienceItemRepository
import com.example.alumniconnect.data.User
import com.example.alumniconnect.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val usersRepository: UsersRepository,
    private val educationRepository: EducationItemRepository,
    private val experienceRepository: ExperienceItemRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getUserProfile()
            _uiState.collect { profileUIState ->
                profileUIState.currentUser?.id?.let { userId ->
                    getUserEducation(userId)
                    getUserExperience(userId)
                }
            }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            usersRepository.getAllUsersStream().collect { userList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        currentUser = userList[0]
                    )
                }
            }
        }
    }

    fun getUserEducation(userId: Int) {
        viewModelScope.launch {
            educationRepository.getEducationForUser(userId).collect { educationList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        educationItems = educationList
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
                        experienceItems = experienceList
                    )
                }
            }
        }
    }

    fun setFieldValue(fieldType: String, fieldItem: FieldItem, fieldValue: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.toMutableMap()
            val updatedFieldList = updatedFields[fieldType]?.map { item ->
                if (item == fieldItem) {
                    item.copy(value = fieldValue)
                } else {
                    item
                }
            }
            updatedFields[fieldType] = updatedFieldList.orEmpty()
            currentState.copy(fields = updatedFields)
        }
    }

    fun setExperienceFieldValue(
        experienceItem: ExperienceItem,
        fieldType: String,
        fieldValue: String
    ) {
        _uiState.update { currentState ->
            val updatedExperienceItems = currentState.experienceItems.map { item ->
                if (item == experienceItem) {
                    when (fieldType) {
                        "role" -> item.copy(role = fieldValue)
                        "company" -> item.copy(company = fieldValue)
                        "startDate" -> item.copy(startDate = fieldValue)
                        "endDate" -> item.copy(endDate = fieldValue)
                        else -> item
                    }
                } else {
                    item
                }
            }
            currentState.copy(experienceItems = updatedExperienceItems)
        }
    }

    fun setEducationFieldValue(
        educationItem: EducationItem,
        fieldType: String,
        fieldValue: String
    ) {
        _uiState.update { currentState ->
            val updatedEducationItems = currentState.educationItems.map { item ->
                if (item == educationItem) {
                    when (fieldType) {
                        "school" -> item.copy(school = fieldValue)
                        "degree" -> item.copy(degree = fieldValue)
                        "startDate" -> item.copy(startDate = fieldValue)
                        "endDate" -> item.copy(endDate = fieldValue)
                        else -> item
                    }
                } else {
                    item
                }
            }
            currentState.copy(educationItems = updatedEducationItems)
        }
    }
}


data class FieldItem(
    val fieldName: String,
    val hidden: Boolean,
    val value: String,
    val icon: Any
)


val fieldIcons = mapOf(
    "Personal" to Icons.Filled.Person,
    "Socials" to R.drawable.social_media,
    "Experience" to R.drawable.suitcase,
    "Education" to R.drawable.graduation,
    "Resume" to Icons.Filled.List,
    "Cover Letter" to Icons.Filled.Build,
)

data class EducationItem(
    var school: String,
    var degree: String,
    var startDate: String,
    var endDate: String
)


data class ExperienceItem(
    var role: String,
    var company: String,
    var startDate: String,
    var endDate: String,
    var isCoop: Boolean = false
)


data class ProfileUIState(
    var currentUser: User? = null,
    var experienceItems: List<ExperienceItem> = listOf(),
    val educationItems: List<EducationItem> = listOf(),
    val id: Int = 0,
    var isStudent: Boolean = currentUser?.isStudent ?: false,
    var firstName: String = currentUser?.firstName ?: "Vaishav",
    var lastName: String = currentUser?.lastName ?: "Dhepe",
    var role: String = currentUser?.role ?: "Software Developer",

    val fields: Map<String, List<FieldItem>> = mapOf(
        "Personal" to
                listOf(
                    FieldItem(
                        fieldName = "First Name",
                        hidden = false,
                        value = currentUser?.firstName ?: "Vaishav",
                        icon = Icons.Filled.AccountCircle
                    ),
                    FieldItem(
                        fieldName = "Last Name",
                        hidden = false,
                        value = currentUser?.contactNumber ?: "Dhepe",
                        icon = Icons.Filled.AccountCircle
                    ),
                    FieldItem(
                        fieldName = "Role",
                        hidden = false,
                        value = currentUser?.role ?: "Software Developer",
                        icon = Icons.Filled.Face
                    ),
                    FieldItem(
                        fieldName = "Contact",
                        hidden = false,
                        value = currentUser?.contactNumber ?: "(902)-318-6993",
                        icon = Icons.Filled.Phone
                    ),
                    FieldItem(
                        fieldName = "Email",
                        hidden = true,
                        value = currentUser?.emailId ?: "test@test.com",
                        icon = Icons.Filled.Email
                    )
                ),
        "Socials" to listOf(
            FieldItem(
                fieldName = "Instagram",
                hidden = false,
                value = currentUser?.instagramId ?: "@testinsta",
                icon = R.drawable.instagram
            ),
            FieldItem(
                fieldName = "LinkedIn",
                hidden = false,
                value = currentUser?.linkedInId ?: "@testlinkedin",
                icon = R.drawable.linkedin
            ),
            FieldItem(
                fieldName = "Facebook",
                hidden = false,
                value = currentUser?.facebookId ?: "@testfacebook",
                icon = R.drawable.facebook
            ),
        ),
        "Experience" to listOf(
            FieldItem(
                fieldName = "Experiences",
                hidden = false,
                value = "1+ experiences",
                icon = Icons.Filled.Info
            )
        ),
        "Education" to listOf(
            FieldItem(
                fieldName = "Education",
                hidden = false,
                value = "1+ Education Details",
                icon = Icons.Filled.Info
            )
        ),
        "Resume" to listOf(
            FieldItem(
                fieldName = "Resume",
                hidden = false,
                value = "View Resume",
                icon = Icons.Filled.Info
            ),
        ),
        "Cover Letter" to listOf(
            FieldItem(
                fieldName = "Cover Letter",
                hidden = false,
                value = "View Cover Letter",
                icon = Icons.Filled.Info
            )
        )

    ),


    var school: String = "STFX",
    var program: String = "MACS",

    var password: String = "",
    var graduationYear: Int? = null,
    var coopYear: Int? = null,
    var isFormValid: Boolean = true,
    var areCredentialsValid: Boolean = false,
    var error: String = "",

    )