package com.example.alumniconnect.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberHorizontalPdfReaderState

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    isResume: Boolean = false,
    userId: Int,
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    var file = "https://pub-2fafbe9774c4496aadd392fe31e1ecef.r2.dev/project-1.pdf"
    val uiState by homeViewModel.uiState.collectAsState()
    val userProfile = uiState.currentList.find { it.id == userId }
    file = if (isResume) ({
        userProfile?.resume
    }).toString() else ({
        userProfile?.coverLetter
    }).toString()
    val pdfState = rememberHorizontalPdfReaderState(
        resource = ResourceType.Remote(file),
        isZoomEnable = true
    )
    Scaffold(topBar = {
        TopBar(
            screenTitle = if (isResume) {
                "Resume"

            } else {
                "Cover Letter"

            },
            canGoBack = true,
            onNavClick = { navController.popBackStack() })

    },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        )
        {
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
        }
    }
}