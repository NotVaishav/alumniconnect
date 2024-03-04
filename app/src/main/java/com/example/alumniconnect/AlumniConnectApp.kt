package com.example.alumniconnect

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.ui.navigation.AlumniConnectNavGraph
import com.example.alumniconnect.ui.screens.signup.SignupViewModel
import com.example.alumniconnect.ui.theme.AlumniConnectTheme


@Composable
fun AlumniConnectApp(navController: NavHostController = rememberNavController()) {
    AlumniConnectNavGraph(
        navController = navController,
    )
}


@Preview(showBackground = true)
@Composable
fun AlumniConnectPreview() {
    AlumniConnectTheme {
        AlumniConnectApp()
    }
}