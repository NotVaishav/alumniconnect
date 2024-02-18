package com.example.alumniconnect.ui.screens.home

import android.widget.ScrollView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.common.PrimaryButton
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun AlumniProfile(navController: NavController, userId: String, modifier: Modifier = Modifier) {
    var fontGrey = colorResource(id = R.color.secondary_grey)
    val state = rememberScrollState()
    val showProfile = state.value == 0
    val animatedPaddingValues = animateDpAsState(if (state.value > 0) 60.dp else 140.dp, label = "")

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ConstraintLayout() {
                val (image, loginForm) = createRefs()
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .height(360.dp)
                        .fillMaxWidth()
                        .constrainAs(image) {
                            top.linkTo(loginForm.top)
                            bottom.linkTo(loginForm.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxSize()
                            .blur(10.dp),
                        contentScale = ContentScale.Crop,

                        )
                    Column {
                        Spacer(modifier = modifier.size(180.dp))
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
                Card(
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    backgroundColor = Color(0xFFF5F5F5),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = animatedPaddingValues.value)
                        .shadow(
                            10.dp,
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )
                        .constrainAs(loginForm) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .padding(horizontal = 15.dp)
                            .verticalScroll(state)
                    ) {
                        Spacer(modifier = modifier.size(50.dp))
                        ProfileSection(fontColor = fontGrey)
                        SocialsRow()
                        Spacer(modifier = modifier.size(10.dp))
                        HitsRow(fontColor = fontGrey)
                        ButtonsRow()
                        CoopExperienceSection(fontColor = fontGrey)
                        Divider(
                            thickness = 1.dp,
                            modifier = modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                        )
                        ExperienceSection(fontColor = fontGrey)
                        Divider(
                            thickness = 1.dp,
                            modifier = modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                        )
                        EducationSection(fontColor = fontGrey)
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = showProfile,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { -50 },
                        animationSpec = tween(durationMillis = 200)
                    ),
                    exit = fadeOut() + slideOutVertically(
                        targetOffsetY = { -200 },
                        animationSpec = tween(durationMillis = 200)
                    )
                ) {
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

            }
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
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = smallPlaceable.height + 100
            )
        }
    }
}


@Composable
fun ProfileSection(modifier: Modifier = Modifier, fontColor: Color) {
    Text(
        text = "Vaishav Dhepe",
        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
    )
    Text(
        text = "Software Engineer",
        color = fontColor,
        modifier = modifier.padding(vertical = 1.dp)
    )
    Spacer(modifier = modifier.size(10.dp))
    Text(
        text = "I'm a positive person. I love to travel and eat. Always available to chat",
        textAlign = TextAlign.Center,
        modifier = modifier.padding(10.dp)
    )
}


@Composable
fun SocialsRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(modifier = modifier.padding(horizontal = 10.dp)) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.linkedin),
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp)
                )
            }
        }
        Box(modifier = modifier.padding(horizontal = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp)

                )
            }
        }
        Box(modifier = modifier.padding(horizontal = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp)
                )
            }
        }
    }
}


@Composable
fun HitsRow(modifier: Modifier = Modifier, fontColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Total Hits",
            modifier = modifier.padding(horizontal = 2.dp)
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .height(60.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            Text(
                text = "120",
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = modifier.size(2.dp))
            Text(
                text = "Resume",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = fontColor,
                fontSize = 12.sp
            )

        }
        Divider(
            modifier = Modifier
                .width(1.dp)
                .padding(vertical = 5.dp)
                .fillMaxHeight()
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = "899",
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Profile",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = fontColor,
                fontSize = 12.sp
            )

        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 5.dp)
                .width(1.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = "80",
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Cover Letter",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = fontColor,
                fontSize = 12.sp
            )

        }
    }
}

@Composable
fun ButtonsRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(vertical = 5.dp)) {
        PrimaryButton(
            onBtnClick = { /*TODO*/ },
            btnText = R.string.view_resume,
            btnColor = Color(0xFFd8d8d8),
            modifier = modifier.weight(1f)
        )
        Spacer(modifier = modifier.size(10.dp))
        PrimaryButton(
            onBtnClick = { /*TODO*/ },
            btnText = R.string.view_cl,
            btnColor = Color(0xFFd8d8d8),
            modifier = modifier.weight(1f)
        )
    }
}

@Composable
fun CoopExperienceSection(modifier: Modifier = Modifier, fontColor: Color) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
    ) {
        Text(
            text = "Co-op Experience",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = modifier
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(
                modifier = modifier.size(50.dp),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.suitcase),
                    contentDescription = null,
                    modifier = modifier.size(40.dp)
                )
            }

            Column(
                modifier = modifier
                    .padding(start = 15.dp),
            ) {
                Text(
                    text = "Software Development Intern",
                    fontWeight = FontWeight.Medium,
                    modifier = modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Dash Hudson",
                    fontSize = 12.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "May 2023 - Dec 2023",
                        fontSize = 10.sp,
                        color = fontColor,
                        modifier = modifier.padding(vertical = 1.dp)
                    )
                    Text(
                        text = "View insights",
                        fontSize = 10.sp,
                        modifier = modifier.padding(vertical = 1.dp),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

        }
    }
}


@Composable
fun ExperienceSection(modifier: Modifier = Modifier, fontColor: Color) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Text(
            text = "Experience",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = modifier
                .padding(vertical = 10.dp)
        ) {
            Box(
                modifier = modifier.size(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.suitcase),
                    contentDescription = null,
                    modifier = modifier.size(40.dp)
                )
            }
            Column(
                modifier = modifier
                    .padding(start = 15.dp),
            ) {
                Text(
                    text = "Python Developer",
                    fontWeight = FontWeight.Medium,
                    modifier = modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Aptagrim Limited",
                    fontSize = 12.sp
                )
                Text(
                    text = "Aug 2021 - Aug 2022",
                    fontSize = 10.sp,
                    color = fontColor,
                    modifier = modifier.padding(vertical = 1.dp)
                )
            }

        }
    }
}


@Composable
fun EducationSection(modifier: Modifier = Modifier, fontColor: Color) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Text(
            text = "Education",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = modifier
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.graduation),
                contentDescription = null,
                modifier = modifier.size(50.dp)
            )
            Column(
                modifier = modifier
                    .padding(start = 15.dp),
            ) {
                Text(
                    text = "St. Francis Xavier University",
                    fontWeight = FontWeight.Medium,
                    modifier = modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Master's degree, Applied Computer Science",
                    fontSize = 12.sp
                )
                Text(
                    text = "Sep 2022 - May 2024",
                    fontSize = 10.sp,
                    color = fontColor,
                    modifier = modifier.padding(vertical = 1.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumniProfilePreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        AlumniProfile(navController = navController, userId = "1")
    }
}