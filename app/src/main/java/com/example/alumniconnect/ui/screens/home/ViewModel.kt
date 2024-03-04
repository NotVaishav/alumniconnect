package com.example.alumniconnect.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.alumniconnect.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onSearchTextChange(text: String) {
        val alumniList = alumniList.filter { alumni ->
            alumni.name.contains(text, ignoreCase = true) || alumni.name.contains(
                text,
                ignoreCase = true
            )
        }
        _uiState.update { currentState ->
            currentState.copy(searchText = text, currentList = alumniList)

        }
    }

    fun onToggleSearch() {
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
}


data class AlumniProfile(val profilePic: Int, val name: String, val profession: String)

val alumniList = listOf<AlumniProfile>(
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "Vaishav Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    ),
    AlumniProfile(
        R.drawable.profile_pic,
        "John Doe",
        "Software Developer"
    )
)

data class HomeUiState(
    var isSearching: Boolean = false,
    var searchText: String = "",
    var currentList: List<AlumniProfile> = alumniList
)