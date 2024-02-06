package com.example.alumniconnect.ui.screens.signup

import android.widget.Space
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.common.PrimaryOutlinedTextField
import com.example.alumniconnect.ui.common.WelcomeTopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme
import kotlinx.coroutines.launch

@Composable
fun StudentSignupScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    viewModel: SignupViewModel,
    onDoneBtn: () -> Unit,
    onFrontBtnClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        WelcomeTopBar(
            onBackBtnClick = onBackBtnClick,
            canNavigateNext = true,
            isNextEnabled = uiState.isFormValid,
            onFrontBtnClick = onFrontBtnClick
        )
        Spacer(modifier = modifier.size(40.dp))
        Text(
            text = "Howdy scholar!", style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Let's get to know you",
            style = MaterialTheme.typography.labelLarge,
            color = Color(R.color.text_grey),
            modifier = modifier.padding()
        )
        Spacer(modifier = modifier.size(20.dp))
        PrimaryOutlinedTextField(
            labelText = "First name",
            value = uiState.firstName,
            onValueChange = { viewModel.setFirstName(it) })
        PrimaryOutlinedTextField(
            labelText = "Last name",
            value = uiState.lastName,
            onValueChange = { viewModel.setLastName(it) })
        PrimaryOutlinedTextField(
            labelText = "School",
            value = uiState.school,
            onValueChange = { viewModel.setSchool(it) })
        PrimaryOutlinedTextField(
            labelText = "Program",
            value = uiState.program,
            onValueChange = { viewModel.setProgram(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onDone = onDoneBtn
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentSignupPreview() {
    AlumniConnectTheme {
        StudentSignupScreen(
            onBackBtnClick = {},
            viewModel = SignupViewModel(),
            onDoneBtn = {},
            onFrontBtnClick = {}
        )
    }
}