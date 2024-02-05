package com.example.alumniconnect.ui.screens.welcome

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Text(text = "Ikade")
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    AlumniConnectTheme {
        WelcomeScreen()
    }
}