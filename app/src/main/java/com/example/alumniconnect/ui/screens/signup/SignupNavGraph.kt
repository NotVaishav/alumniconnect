package com.example.alumniconnect.ui.screens.signup

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations


fun NavGraphBuilder.signupGraph(
    navController: NavController,
    signupViewModel: SignupViewModel
) {
    navigation(
        startDestination = AlumniConnectNavDestinations.Signup.title,
        route = AlumniConnectNavDestinations.Signup.route
    ) {
        composable(route = AlumniConnectNavDestinations.Signup.title) {
            SignupScreen(
                onBackBtnClick = {
                    navController.navigate(AlumniConnectNavDestinations.Welcome.title)
                },
                onStudentBtnClick = {
                    signupViewModel.updateUserState(isStudent = true)
                    navController.navigate(
                        AlumniConnectNavDestinations.Student.title
                    )

                },
                onAlumnusBtnClick = {
                    signupViewModel.updateUserState(isStudent = false)
                    navController.navigate(
                        AlumniConnectNavDestinations.Student.title
                    )

                },
                navController = navController,
                viewModel = signupViewModel
            )
        }
        composable(route = AlumniConnectNavDestinations.Student.title) {
            StudentSignupScreen(
                onBackBtnClick = {
                    navController.navigate(
                        AlumniConnectNavDestinations.Signup.title
                    )

                },
                onDoneBtn = { signupViewModel.onDone(navController) },
                onFrontBtnClick = { signupViewModel.onDone(navController, viaNextBtn = true) },
                viewModel = signupViewModel
            )
        }
        composable(route = AlumniConnectNavDestinations.Credentials.title) {
            CredentialsScreen(
                onBackBtnClick = {
                    navController.popBackStack()
                },
                navController = navController,
                viewModel = signupViewModel
            )
        }
    }
}