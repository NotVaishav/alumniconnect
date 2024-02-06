package com.example.alumniconnect.ui.screens.signup

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.common.buttons.PrimaryButton
import com.example.alumniconnect.ui.screens.welcome.WelcomeScreen
import com.example.alumniconnect.ui.theme.AlumniConnectTheme


@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),


        ) {
        IconButton(
            onClick = {},
            modifier = modifier.size(20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.back), contentDescription = null)
        }
        Spacer(modifier = modifier.size(18.dp))
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
            onBtnClick = {},
            btnText = R.string.student,
            icon = R.drawable.next
        )
        PrimaryButton(
            onBtnClick = {},
            btnText = R.string.alumni,
            icon = R.drawable.next
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    AlumniConnectTheme {
        SignupScreen()
    }
}