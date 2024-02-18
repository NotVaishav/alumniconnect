package com.example.alumniconnect.ui.common

import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.screens.signup.CredentialsScreen
import com.example.alumniconnect.ui.screens.signup.SignupViewModel
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
    @StringRes btnText: Int,
    @DrawableRes icon: Int? = null,
    btnColor: Color = Color(0xFFf0f0f0)
) {
    val context = LocalContext.current
    Button(
        onClick = onBtnClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = Color.Black,
        ),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = context.getString(btnText),
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = modifier
                        .size(14.dp)
                        .weight(0.05f)
                )
            }

        }
    }
}


@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
    @StringRes btnText: Int,
    isLoading: Boolean = false
) {
    val context = LocalContext.current
    val loadingTransitionState = remember { MutableTransitionState(isLoading) }
    val transition = updateTransition(loadingTransitionState, label = "Loading Transition")

    Button(
        onClick = onBtnClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFf0f0f0),
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = isLoading,
            label = "",
        ) { isLoadingState ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .size(26.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoadingState) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        color = Color(R.color.text_grey),
                    )
                } else {
                    Text(
                        text = context.getString(btnText),
                        modifier = modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                }
            }
            LaunchedEffect(isLoading) {
                loadingTransitionState.targetState = isLoading
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonPreview() {
    AlumniConnectTheme {
        LoadingButton(
            onBtnClick = {}, btnText = R.string.begin, isLoading = true
        )
    }
}