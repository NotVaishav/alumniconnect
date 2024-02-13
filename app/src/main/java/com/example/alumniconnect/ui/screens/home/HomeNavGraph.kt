package com.example.alumniconnect.ui.screens.home

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.signup.SignupScreen
import com.example.alumniconnect.ui.screens.signup.SignupViewModel


fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = AlumniConnectNavDestinations.Alumni.title,
        route = AlumniConnectNavDestinations.Home.title
    ) {
        composable(route = AlumniConnectNavDestinations.Alumni.title) {
            AlumniHomeScreen(navController = navController)
        }
        composable(route = AlumniConnectNavDestinations.AlumniDirectory.title) { backStackEntry ->
            val domainId = backStackEntry.arguments?.getString("domainId")

            if (domainId != null) {
                Log.d("IKADE", domainId)
                AlumniDirectoryScreen(navController = navController, domainId = domainId)
            }
        }
        composable(route = AlumniConnectNavDestinations.Profile.title) {
            ProfileScreen(navController = navController)
        }
    }

}