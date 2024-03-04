package com.example.alumniconnect.ui.screens.profile

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.home.AlumniDirectoryScreen
import com.example.alumniconnect.ui.screens.home.AlumniHomeScreen
import com.example.alumniconnect.ui.screens.home.AlumniProfile


fun NavGraphBuilder.profileGraph(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    navigation(
        startDestination = AlumniConnectNavDestinations.Profile.title,
        route = AlumniConnectNavDestinations.Profile.route
    ) {
        composable(route = AlumniConnectNavDestinations.Profile.title) {
            ProfileScreen(navController = navController, profileViewModel = profileViewModel)
        }
        composable(route = AlumniConnectNavDestinations.ProfileEdit.title) { backStackEntry ->
            val editType = backStackEntry.arguments?.getString("editType")
            if (editType != null) {
                EditProfile(
                    navController = navController,
                    editType = editType,
                    profileViewModel = profileViewModel
                )
            }
        }
    }

}