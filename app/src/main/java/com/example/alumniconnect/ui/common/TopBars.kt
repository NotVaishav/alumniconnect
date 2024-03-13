package com.example.alumniconnect.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.alumniconnect.R

@Composable
fun WelcomeTopBar(
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit,
    canNavigateNext: Boolean = false,
    isNextEnabled: Boolean = false,
    onFrontBtnClick: () -> Unit = {}
) {
    Spacer(modifier = modifier.size(20.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()) {
        IconButton(
            onClick = onBackBtnClick,
            modifier = modifier.size(20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.back), contentDescription = null)
        }
        if (canNavigateNext) {
            IconButton(
                onClick = onFrontBtnClick,
                modifier = modifier.size(20.dp),
                enabled = isNextEnabled,
            ) {
                Icon(painter = painterResource(id = R.drawable.next), contentDescription = null)
            }
        }

    }

}