package com.example.alumniconnect.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.alumniconnect.AlumniConnectApplication
import com.example.alumniconnect.ui.screens.signup.SignupViewModel
import com.example.alumniconnect.ui.screens.welcome.WelcomeViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SignupViewModel(alumniConnectApplication().container.usersRepository)
        }
        initializer {
            WelcomeViewModel()
        }

    }
}

fun CreationExtras.alumniConnectApplication(): AlumniConnectApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AlumniConnectApplication)