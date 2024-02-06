package com.example.alumniconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alumniconnect.ui.screens.login.LoginScreen
import com.example.alumniconnect.ui.screens.signup.SignupScreen
import com.example.alumniconnect.ui.screens.welcome.WelcomeScreen

enum class AlumniConnectNavDestinations(val title: String) {
    Welcome(title = "welcome"),
    Signup(title = "signup"),
    Login(title = "login")
}

@Composable
fun AlumniConnectNavGraph(
    navController: NavHostController
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
        composable(route = AlumniConnectNavDestinations.Signup.title) {
            SignupScreen()
        }
        composable(route = AlumniConnectNavDestinations.Login.title) {
            LoginScreen()
        }
    }
}