package com.example.alumniconnect.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alumniconnect.AlumniConnectApplication
import com.example.alumniconnect.ui.screens.home.HomeViewModel
import com.example.alumniconnect.ui.screens.profile.ProfileViewModel
import com.example.alumniconnect.ui.screens.signup.SignupViewModel
import com.example.alumniconnect.ui.screens.welcome.WelcomeViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SignupViewModel(
                alumniConnectApplication().container.usersRepository,
                alumniConnectApplication().userStoreData,
                alumniConnectApplication().container.domainRepository,
            )
        }
        initializer {
            WelcomeViewModel()
        }
        initializer {
            ProfileViewModel(
                alumniConnectApplication().container.usersRepository,
                alumniConnectApplication().container.educationItemRepository,
                alumniConnectApplication().container.experienceItemRepository,
            )
        }
        initializer {
            HomeViewModel(
                alumniConnectApplication().container.usersRepository,
                alumniConnectApplication().container.domainRepository,
                alumniConnectApplication().container.educationItemRepository,
                alumniConnectApplication().container.experienceItemRepository,
            )
        }
    }
}

fun CreationExtras.alumniConnectApplication(): AlumniConnectApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AlumniConnectApplication)