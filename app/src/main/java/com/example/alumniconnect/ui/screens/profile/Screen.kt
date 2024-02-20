package com.example.alumniconnect.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.alumniconnect.ui.screens.home.BottomNavBar
import com.example.alumniconnect.ui.screens.home.TopBar

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