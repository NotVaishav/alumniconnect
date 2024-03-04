package com.example.alumniconnect.ui.screens.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.lifecycle.ViewModel
import com.example.alumniconnect.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

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
    val id: Int = 0,
    var isStudent: Boolean = true,
    var firstName: String = "Vaishav",
    var lastName: String = "Dhepe",
    var role: String = "Software Developer",

    val fields: Map<String, List<FieldItem>> = mapOf(
        "Personal" to
                listOf(
                    FieldItem(
                        fieldName = "Contact",
                        hidden = false,
                        value = "(902)-318-6993",
                        icon = Icons.Filled.Phone
                    ),
                    FieldItem(
                        fieldName = "Email",
                        hidden = true,
                        value = "vaishavtest@gmail.com",
                        icon = Icons.Filled.Email
                    )
                ),
        "Socials" to listOf(
            FieldItem(
                fieldName = "Instagram",
                hidden = false,
                value = "@notvaishav",
                icon = R.drawable.instagram
            ),
            FieldItem(
                fieldName = "LinkedIn",
                hidden = false,
                value = "@vaishavdhepe",
                icon = R.drawable.linkedin
            ),
            FieldItem(
                fieldName = "Facebook",
                hidden = false,
                value = "@vaishav",
                icon = R.drawable.facebook
            ),
        ),
        "Experience" to listOf(
            FieldItem(
                fieldName = "Experiences",
                hidden = false,
                value = "2+ experiences",
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

    var experienceItems: List<ExperienceItem> = listOf(
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
    val educationItems: List<EducationItem> = listOf(
        EducationItem(
            "St. Francis Xavier University",
            "Master's degree, Applied Computer Science",
            "Sep 2022",
            endDate = "May 2024"
        )
    ),

    var school: String = "STFX",
    var program: String = "MACS",

    var password: String = "",
    var graduationYear: Int? = null,
    var coopYear: Int? = null,
    var isFormValid: Boolean = true,
    var areCredentialsValid: Boolean = false,
    var error: String = ""
)