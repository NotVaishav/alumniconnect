package com.example.alumniconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alumniconnect.ui.screens.welcome.WelcomeScreen

enum class AlumniConnectNavDestinations(val title: String) {
    Welcome(title = "welcome"),
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
            WelcomeScreen()
        }
    }
}