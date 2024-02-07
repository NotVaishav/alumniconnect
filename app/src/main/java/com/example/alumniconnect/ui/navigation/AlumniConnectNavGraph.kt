package com.example.alumniconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.screens.login.LoginScreen
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
}

@Composable
fun AlumniConnectNavGraph(
    navController: NavHostController,
    signupViewModel: SignupViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    NavHost(
        navController = navController,
        startDestination = AlumniConnectNavDestinations.Welcome.title
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
    }
}