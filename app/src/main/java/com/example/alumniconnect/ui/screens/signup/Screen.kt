package com.example.alumniconnect.ui.screens.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.common.PrimaryButton
import com.example.alumniconnect.ui.common.WelcomeTopBar
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.profile.ProfileViewModel
import com.example.alumniconnect.ui.theme.AlumniConnectTheme
import kotlinx.coroutines.launch


@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    onStudentBtnClick: () -> Unit,
    onAlumnusBtnClick: () -> Unit,
    navController: NavController,
    viewModel: SignupViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val textColor = context.getColor(R.color.text_grey)
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        WelcomeTopBar(onBackBtnClick = onBackBtnClick)
        Spacer(modifier = modifier.size(140.dp))
        Text(
            text = context.getString(R.string.get_started),
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = context.getString(R.string.are_you),
            style = MaterialTheme.typography.labelLarge,
            color = Color(textColor),
            modifier = modifier.padding(start = 20.dp)
        )
        Column(modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            PrimaryButton(
                onBtnClick = onStudentBtnClick,
                btnText = R.string.student,
                icon = R.drawable.next,
            )
            Spacer(modifier = modifier.size(10.dp))
            PrimaryButton(
                onBtnClick = {
                    scope.launch {
                        viewModel.updateUserState(false)
                        navController.navigate(AlumniConnectNavDestinations.Student.title)
                    }
                },
                btnText = R.string.alumni,
                icon = R.drawable.next
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        SignupScreen(
            onBackBtnClick = {},
            onStudentBtnClick = {},
            onAlumnusBtnClick = {},
            navController = navController
        )
    }
}