package com.example.alumniconnect.ui.common.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
    @StringRes btnText: Int,
    @DrawableRes icon: Int? = null,
) {
    val context = LocalContext.current
    Button(
        onClick = onBtnClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFf0f0f0),
            contentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
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
                textAlign = TextAlign.Center
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