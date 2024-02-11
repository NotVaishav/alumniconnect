package com.example.alumniconnect.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.signup.SignupScreen
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopBar(screenTitle = "Alumnus") },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text(text = "Test")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopBar(screenTitle = "Profile") },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text(text = "Profile")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, screenTitle: String) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = screenTitle,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium
        )

    }, modifier = modifier.padding(5.dp))
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        AlumniScreen(navController = navController)
    }
}