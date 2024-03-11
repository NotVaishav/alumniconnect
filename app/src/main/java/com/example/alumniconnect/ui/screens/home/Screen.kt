package com.example.alumniconnect.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.signup.SignupScreen
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniHomeScreen(
    navController: NavController, modifier: Modifier = Modifier,

    ) {
    Scaffold(
        topBar = { TopBar(screenTitle = "Home") },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->

        Column(modifier = modifier.padding(innerPadding)) {
            FeaturedProfile(navController = navController)
            DomainsSection(navController = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    screenTitle: String,
    onNavClick: () -> Unit = {},
    canGoBack: Boolean = false,
    showAction: Boolean = false,
    onCheckAction: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = screenTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )

    }, modifier = modifier.padding(5.dp),
        actions = {
            if (showAction) {
                IconButton(onClick = onCheckAction) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                }
            }

        },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onNavClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        AlumniHomeScreen(navController = navController)
    }
}