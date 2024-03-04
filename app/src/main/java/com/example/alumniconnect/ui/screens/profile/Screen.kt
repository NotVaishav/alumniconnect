package com.example.alumniconnect.ui.screens.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.home.AlumniProfile
import com.example.alumniconnect.ui.screens.home.BottomNavBar
import com.example.alumniconnect.ui.screens.home.Profile
import com.example.alumniconnect.ui.screens.home.TopBar
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun ProfileScreen(
    navController: NavController, modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val fontGrey = colorResource(id = R.color.secondary_grey)

    Scaffold(
        topBar = { TopBar(screenTitle = "Profile") },
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Box(modifier = modifier.height(150.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize()
                        .blur(10.dp),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = modifier.size(25.dp))
            Column(
                modifier = modifier
                    .padding(vertical = 20.dp, horizontal = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = modifier.padding(vertical = 15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vaishav Dhepe",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(text = "Software Developer", color = fontGrey)
                }
                Spacer(modifier = modifier.size(10.dp))
                for (item in uiState.fields) {
                    InformationSection(
                        mainFieldText = item.key,
                        mainFieldIcon = fieldIcons[item.key]!!,
                        subFields = item.value,
                        navController = navController
                    )
                    Spacer(modifier = modifier.size(15.dp))
                }
            }
        }
    }
    OverlappingImageBox {
        Box(
            modifier = modifier
                .size(100.dp)
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color.Black,
                    shape = CircleShape
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = null,
                modifier = modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color.Black,
                        shape = CircleShape
                    )
            )
        }
    }
}


@Composable
fun OverlappingImageBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val smallBox = measurables[0]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight + smallPlaceable.height,
        ) {
            smallPlaceable.placeRelative(
                x = (smallPlaceable.width / 2) - 100,
                y = smallPlaceable.height + 400
            )
        }
    }
}


@Composable
fun InformationSection(
    modifier: Modifier = Modifier,
    mainFieldText: String,
    mainFieldIcon: Any,
    subFields: List<FieldItem>,
    navController: NavController,
    optionForAll: Boolean = false
) {
    val fontGrey = colorResource(id = R.color.secondary_grey)
    var showContents by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.bottomBorder(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { showContents = !showContents }

            ) {
                GetUpdatedIcon(icon = mainFieldIcon)
                Text(
                    text = mainFieldText,
                    modifier = modifier
                        .padding(start = 20.dp)
                        .weight(1f),
                    fontSize = 22.sp,
                )
                IconButton(
                    onClick = { showContents = !showContents },
                    modifier = modifier.padding(0.dp)
                ) {
                    AnimatedContent(targetState = showContents, label = "") { showContents ->
                        if (showContents) {
                            Icon(
                                imageVector = Icons.Sharp.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Sharp.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            if (showContents) {
                Column(
                    modifier = modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                ) {
                    Row(
                        modifier = modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GetUpdatedIcon(icon = subFields[0].icon, tint = fontGrey)
                        Text(
                            text = subFields[0].value,
                            modifier = modifier
                                .padding(start = 10.dp)
                                .weight(1f),
                            color = fontGrey
                        )
                        Box(
                            modifier = modifier
                                .clip(CircleShape)
                                .wrapContentSize()
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true),
                                    onClick = {
                                        navController.navigate("${AlumniConnectNavDestinations.ProfileEdit.route}/${mainFieldText}")
                                    },
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (optionForAll) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = fontGrey,
                                    modifier = modifier
                                        .size(16.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = null,
                                    tint = fontGrey,
                                    modifier = modifier
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                    for (field in subFields.drop(1)) {
                        Row(modifier = modifier.padding(vertical = 4.dp)) {
                            GetUpdatedIcon(icon = field.icon, tint = fontGrey)
                            Text(
                                text = field.value,
                                modifier = modifier.padding(start = 10.dp),
                                color = fontGrey
                            )
                            if (optionForAll) {
                                Box(
                                    modifier = modifier
                                        .clip(CircleShape)
                                        .wrapContentSize()
                                        .size(20.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(bounded = true),
                                            onClick = {
                                                navController.navigate("${AlumniConnectNavDestinations.ProfileEdit.route}/${mainFieldText}")
                                            },
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowRight,
                                        contentDescription = null,
                                        tint = fontGrey,
                                        modifier = modifier
                                            .size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun GetUpdatedIcon(modifier: Modifier = Modifier, icon: Any, tint: Color = Color.Black) {
    when (icon) {
        is ImageVector -> {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = modifier.size(24.dp)
            )
        }

        is Int -> {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = tint,
                modifier = modifier.size(24.dp)
            )
        }
    }
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        ProfileScreen(navController = navController)
    }
}