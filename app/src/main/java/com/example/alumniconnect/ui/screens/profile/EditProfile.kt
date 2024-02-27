package com.example.alumniconnect.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.common.PrimaryOutlinedTextField
import com.example.alumniconnect.ui.screens.home.BottomNavBar
import com.example.alumniconnect.ui.screens.home.TopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme


@Composable
fun EditProfile(
    navController: NavController,
    modifier: Modifier = Modifier,
    editType: String,
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val currentForm = uiState.fields[editType]
    Scaffold(topBar = {
        TopBar(
            screenTitle = "Edit $editType",
            canGoBack = true,
            onNavClick = { navController.popBackStack() })
    },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (editType == "Experience") {
                for (item in uiState.experienceItems) {
                    Card(
                        modifier = modifier
                            .padding(bottom = 20.dp)
                            .shadow(8.dp, RoundedCornerShape(10.dp))
                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                    ) {
                        Column(modifier = modifier.padding(5.dp)) {
                            PrimaryOutlinedTextField(
                                labelText = "Role",
                                value = item.role,
                                onValueChange = {
                                    profileViewModel.setExperienceFieldValue(item, "role", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "Company",
                                value = item.company,
                                onValueChange = {
                                    profileViewModel.setExperienceFieldValue(item, "company", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "Start Date",
                                value = item.startDate,
                                onValueChange = {
                                    profileViewModel.setExperienceFieldValue(item, "startDate", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "End Date",
                                value = item.endDate,
                                onValueChange = {
                                    profileViewModel.setExperienceFieldValue(item, "endDate", it)
                                }
                            )
                        }


                    }
                }
            } else if (editType == "Education") {
                for (item in uiState.educationItems) {
                    Card(
                        modifier = modifier
                            .padding(vertical = 10.dp)
                            .shadow(8.dp, RoundedCornerShape(10.dp))
                            .background(Color(0xFFF5F5F5), RoundedCornerShape(10.dp))
                    ) {
                        Column(modifier = modifier.padding(5.dp)) {
                            PrimaryOutlinedTextField(
                                labelText = "School",
                                value = item.school,
                                onValueChange = {
                                    profileViewModel.setEducationFieldValue(item, "school", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "Degree",
                                value = item.degree,
                                onValueChange = {
                                    profileViewModel.setEducationFieldValue(item, "degree", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "Start Date",
                                value = item.startDate,
                                onValueChange = {
                                    profileViewModel.setEducationFieldValue(item, "startDate", it)
                                }
                            )
                            PrimaryOutlinedTextField(
                                labelText = "End Date",
                                value = item.endDate,
                                onValueChange = {
                                    profileViewModel.setEducationFieldValue(item, "endDate", it)
                                }
                            )
                        }


                    }
                }
            } else {
                if (currentForm != null) {
                    for (field in currentForm) {
                        PrimaryOutlinedTextField(
                            labelText = field.fieldName,
                            value = field.value,
                            onValueChange = {
                                profileViewModel.setFieldValue(editType, field, it)
                            }
                        )
                    }
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