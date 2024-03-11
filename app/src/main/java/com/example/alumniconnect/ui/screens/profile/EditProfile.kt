package com.example.alumniconnect.ui.screens.profile

import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.common.PdfRender
import com.example.alumniconnect.ui.common.PrimaryButton
import com.example.alumniconnect.ui.common.PrimaryOutlinedTextField
import com.example.alumniconnect.ui.screens.home.BottomNavBar
import com.example.alumniconnect.ui.screens.home.TopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.HorizontalPdfReaderState
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberHorizontalPdfReaderState
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths


@Composable
fun EditProfile(
    navController: NavController,
    modifier: Modifier = Modifier,
    editType: String,
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var file: String? = null
    var pdfState: HorizontalPdfReaderState? = null
    val uiState by profileViewModel.uiState.collectAsState()
    val currentForm = uiState.fields[editType]
    if (editType == "Resume") {
        file = uiState.currentUser?.resume
    } else if (editType == "Cover Letter") {
        file = uiState.currentUser?.coverLetter
    }
    if (editType == "Resume" || editType == "Cover Letter") {
        pdfState = rememberHorizontalPdfReaderState(
            resource = ResourceType.Remote(file!!),
            isZoomEnable = true
        )
    } else {
        pdfState = rememberHorizontalPdfReaderState(
            resource = ResourceType.Asset(R.raw.madmidterm1),
            isZoomEnable = true
        )
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Scaffold(topBar = {
        TopBar(
            screenTitle = "Edit $editType",
            canGoBack = true,
            onNavClick = { navController.popBackStack() },
        )

    },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (editType == "Resume") {
                Spacer(modifier = modifier.size(20.dp))
                HorizontalPDFReader(
                    state = pdfState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                )
                Spacer(modifier = modifier.size(20.dp))
                Text(
                    text = "Page: ${pdfState.currentPage + 1}/${pdfState.pdfPageCount}",
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 4.dp,
                            top = 8.dp
                        )
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                PrimaryButton(
                    onBtnClick = { launcher.launch("docs/*") },
                    btnText = R.string.edit_resume
                )
            } else if (editType == "Cover Letter") {
                Spacer(modifier = modifier.size(20.dp))
                HorizontalPDFReader(
                    state = pdfState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                )
                Spacer(modifier = modifier.size(20.dp))
                Text(
                    text = "Page: ${pdfState.currentPage + 1}/${pdfState.pdfPageCount}",
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 4.dp,
                            top = 8.dp
                        )
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                PrimaryButton(
                    onBtnClick = { launcher.launch("docs/*") },
                    btnText = R.string.edit_cl
                )
            } else if (editType == "Experience") {
                for (item in uiState.experienceItems) {
                    Card(
                        modifier = modifier
//                            .padding(vertical = 20.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
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
//                        Spacer(modifier = modifier.size(10.dp))
                        Divider(thickness = 2.dp, modifier = modifier.padding(vertical = 20.dp))
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