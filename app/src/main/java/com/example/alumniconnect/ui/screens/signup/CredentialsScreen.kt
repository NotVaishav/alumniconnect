package com.example.alumniconnect.ui.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.alumniconnect.ui.common.WelcomeTopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun CredentialsScreen(
    viewModel: SignupViewModel,
    onBackBtnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    Column {
        WelcomeTopBar(onBackBtnClick = onBackBtnClick)
        Text(text = "Hey ${uiState.firstName}!")
    }
}


@Preview(showBackground = true)
@Composable
fun CredentialsPreview() {
    AlumniConnectTheme {
        CredentialsScreen(
            viewModel = SignupViewModel(),
            onBackBtnClick = {}
        )
    }
}