package com.example.alumniconnect.ui.screens.home

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.theme.AlumniConnectTheme


sealed class Screen(val route: String, val routeList: Array<String>, val icon: ImageVector) {
    data object Alumnus : Screen(
        AlumniConnectNavDestinations.Alumni.title,
        arrayOf(
            AlumniConnectNavDestinations.Alumni.title,
            AlumniConnectNavDestinations.AlumniDirectory.title,
            AlumniConnectNavDestinations.AlumniProfile.title
        ),
        Icons.Filled.Person
    )

    data object Profile :
        Screen(
            AlumniConnectNavDestinations.Profile.title,
            arrayOf(AlumniConnectNavDestinations.Profile.title),
            Icons.Filled.AccountCircle
        )
}

val items = listOf(
    Screen.Alumnus,
    Screen.Profile,
)

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFFF5F5F5),
        modifier = modifier
            .height(78.dp)
            .fillMaxSize()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val primaryGreyColor = colorResource(id = R.color.secondary_grey)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            items.forEach { item ->
                NavigationBarItem(

                    selected = currentRoute in item.routeList,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Log.d("ROUTE", AlumniConnectNavDestinations.AlumniDirectory.title)
                        if (currentRoute in item.routeList) {
                            Icon(item.icon, contentDescription = null, tint = Color.Black)
                        } else {
                            Icon(item.icon, contentDescription = null, tint = primaryGreyColor)
                        }
                    },
//                label = { Text(item.resourceId) },
                    colors = NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = Color.Blue,
                            indicatorColor = Color(0xFFF5F5F5),
                            ),

                    modifier = modifier.wrapContentSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        BottomNavBar(navController = navController)
    }
}