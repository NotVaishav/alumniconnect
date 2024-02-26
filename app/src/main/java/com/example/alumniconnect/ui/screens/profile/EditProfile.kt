package com.example.alumniconnect.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.ui.common.PrimaryOutlinedTextField
import com.example.alumniconnect.ui.screens.home.BottomNavBar
import com.example.alumniconnect.ui.screens.home.TopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

data class FieldItem(val value: String, val hidden: Boolean)

val fields = mapOf(
    "Personal" to listOf(
        FieldItem(value = "Contact", hidden = false),
        FieldItem(value = "Email", hidden = true)
    )
)

@Composable
fun EditProfile(navController: NavController, modifier: Modifier = Modifier, editType: String) {
    val currentForm = fields[editType]
    Scaffold(topBar = {
        TopBar(
            screenTitle = "Edit $editType",
            canGoBack = true,
            onNavClick = { navController.popBackStack() })
    },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding).padding(horizontal = 20.dp)
        ) {
            if (currentForm != null) {
                for (field in currentForm) {
                    PrimaryOutlinedTextField(
                        labelText = field.value,
                        value = "test",
                        onValueChange = {})
                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        EditProfile(navController = navController, editType = "Test")
    }
}