package com.example.alumniconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.alumniconnect.data.UserDataStore
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.screens.home.homeGraph
import com.example.alumniconnect.ui.screens.login.LoginScreen
import com.example.alumniconnect.ui.screens.profile.ProfileScreen
import com.example.alumniconnect.ui.screens.profile.ProfileViewModel
import com.example.alumniconnect.ui.screens.profile.profileGraph
import com.example.alumniconnect.ui.screens.signup.SignupScreen
import com.example.alumniconnect.ui.screens.signup.SignupUiState
import com.example.alumniconnect.ui.screens.signup.SignupViewModel
import com.example.alumniconnect.ui.screens.signup.signupGraph
import com.example.alumniconnect.ui.screens.welcome.WelcomeScreen

enum class AlumniConnectNavDestinations(val title: String, val route: String) {
    Welcome(title = "welcome", route = "welcomeRoute"),
    Signup(title = "signup", route = "signupRoute"),
    Login(title = "login", route = "loginRoute"),
    Student(title = "student", route = "studentRoute"),
    Alumnus(title = "alumnus", route = "alumnusRoute"),
    Credentials(title = "credentials", route = "credentialsRoute"),
    Home(title = "home", route = "homeRoute"),
    Content(title = "content/{type}/{userId}", route = "content"),
    Alumni(title = "alumni", route = "alumniRoute"),
    Profile(title = "profile", route = "profileRoute"),
    ProfileMain(title = "profileMain", route = "profileMainRoute"),
    ProfileEdit(title = "profileEdit/{editType}", route = "profileEdit"),
    AlumniDirectory(title = "alumniDirectory/{domainId}", route = "alumniDirectory"),
    AlumniProfile(title = "alumniProfile/{userId}", route = "alumniProfile"),
}

@Composable
fun AlumniConnectNavGraph(
    navController: NavHostController,
    signupViewModel: SignupViewModel = viewModel(factory = AppViewModelProvider.Factory),
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    val signupUiState by signupViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (signupUiState.isUserLoggedIn) {
            AlumniConnectNavDestinations.Home.title
        } else {
            AlumniConnectNavDestinations.Welcome.title
        }
    ) {
        composable(route = AlumniConnectNavDestinations.Welcome.title) {
            WelcomeScreen(
                onStartBtnClick = { navController.navigate(AlumniConnectNavDestinations.Signup.title) },
                onAccountBtnClick = { navController.navigate(AlumniConnectNavDestinations.Login.title) })
        }
        signupGraph(navController = navController, signupViewModel)
        composable(route = AlumniConnectNavDestinations.Login.title) {
            LoginScreen()
        }
        homeGraph(navController = navController)
        profileGraph(navController = navController, profileViewModel)
    }
}