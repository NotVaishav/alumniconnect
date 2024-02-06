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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.common.PrimaryButton
import com.example.alumniconnect.ui.common.WelcomeTopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme


@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    onStudentBtnClick: () -> Unit,
    onAlumnusBtnClick: () -> Unit,
) {
    val context = LocalContext.current
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
            color = Color(R.color.text_grey),
            modifier = modifier.padding(start = 20.dp)
        )
        PrimaryButton(
            onBtnClick = onStudentBtnClick,
            btnText = R.string.student,
            icon = R.drawable.next
        )
        PrimaryButton(
            onBtnClick = onAlumnusBtnClick,
            btnText = R.string.alumni,
            icon = R.drawable.next
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    AlumniConnectTheme {
        SignupScreen(onBackBtnClick = {}, onStudentBtnClick = {}, onAlumnusBtnClick = {})
    }
}