package com.example.alumniconnect.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.ui.theme.AlumniConnectTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.common.PrimaryButton


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = viewModel(),
    onStartBtnClick: () -> Unit,
    onAccountBtnClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(context.getColor(R.color.white)))
    ) {
        Spacer(modifier = modifier.height(100.dp))
        Divider(
            color = Color(context.getColor(R.color.primary_grey)),
            thickness = 5.dp,
            modifier = modifier
                .padding(horizontal = 20.dp)
        )
        Text(
            text = context.getString(R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(vertical = 15.dp)
        )
        Divider(
            color = Color(context.getColor(R.color.primary_grey)),
            thickness = 5.dp,
            modifier = modifier
                .padding(horizontal = 20.dp)
        )
        Text(
            text = context.getString(R.string.welcome_desc),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = modifier
                .width(250.dp)
                .padding(vertical = 20.dp)
        )
        Box(
            modifier = modifier
                .size(270.dp)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_connections),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier.fillMaxSize()
            )
        }
        PrimaryButton(
            onBtnClick = onStartBtnClick,
            btnText = R.string.btn_get_started
        )
        ClickableText(
            text = AnnotatedString(context.getString(R.string.txt_btn_already_account)),
            style = MaterialTheme.typography.bodySmall.merge(TextStyle(textDecoration = TextDecoration.Underline)),
            onClick = onAccountBtnClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    AlumniConnectTheme {
        WelcomeScreen(onStartBtnClick = {}, onAccountBtnClick = {})
    }
}