package com.example.alumniconnect.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.common.LoadingButton
import com.example.alumniconnect.ui.common.PrimaryButton
import com.example.alumniconnect.ui.common.PrimaryOutlinedTextField
import com.example.alumniconnect.ui.common.WelcomeTopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme
import kotlinx.coroutines.launch

@Composable
fun CredentialsScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackBtnClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var insertionResult by remember { mutableStateOf<Result<Unit>?>(null) }


    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            }
    ) {
        WelcomeTopBar(onBackBtnClick = onBackBtnClick)
        Spacer(modifier = modifier.size(40.dp))
        Text(
            text = "Hey! ${uiState.firstName},",
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = "Let's finalize your account", style = MaterialTheme.typography.labelLarge,
            color = Color(R.color.text_grey),
        )
        Spacer(modifier = modifier.size(20.dp))
        PrimaryOutlinedTextField(
            labelText = "Email",
            value = uiState.emailId,
            onValueChange = { viewModel.setEmail(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        PrimaryOutlinedTextField(
            labelText = "Password",
            value = uiState.password,
            onValueChange = { viewModel.setPassword(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )
        Spacer(modifier = modifier.size(10.dp))
        PrimaryButton(
            onBtnClick = { coroutineScope.launch { viewModel.saveUser() } },
            btnText = R.string.begin
        )
        if (uiState.error == "success") {
            Toast.makeText(context, "User inserted successfully", Toast.LENGTH_SHORT).show()
        } else if (uiState.error.isNotBlank()) {
            Toast.makeText(context, "Error: ${uiState.error}", Toast.LENGTH_SHORT).show()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CredentialsPreview() {
    AlumniConnectTheme {
        CredentialsScreen(
            onBackBtnClick = {}
        )
    }
}