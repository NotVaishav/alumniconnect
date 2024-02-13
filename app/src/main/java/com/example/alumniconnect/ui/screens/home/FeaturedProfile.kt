package com.example.alumniconnect.ui.screens.home

import android.widget.HorizontalScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

data class Profile(val name: String, val image: Int)

@Composable
fun FeaturedProfile(modifier: Modifier = Modifier) {
    val profileList = listOf(
        Profile("John Doe", R.drawable.profile_pic),
        Profile("Jane Smith", R.drawable.login_img),
        Profile("Mike Johnson", R.drawable.profile_pic),
        Profile("Mike Johnson", R.drawable.profile_pic),
        Profile("Mike Johnson", R.drawable.profile_pic),
        Profile("Mike Johnson", R.drawable.profile_pic),
        Profile("Mike Johnson", R.drawable.profile_pic),
        Profile("Mike Johnson", R.drawable.login_img),
        Profile("Mike Johnson", R.drawable.profile_pic),
        // Add more profiles as needed
    )
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Featured Profiles",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.padding(10.dp),
            fontWeight = FontWeight.Medium
        )
        LazyRow( modifier = modifier.padding(horizontal = 10.dp)
        ) {
            itemsIndexed(profileList) { index, item ->
                Card(
                    shape = RoundedCornerShape(0.dp),
                    modifier = modifier
                        .width(160.dp)
                        .height(180.dp)
                        .background(color = Color.Transparent)
                ) {
                    Column(
                        modifier = modifier
                            .background(color = Color.White)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    spotColor = Color.Black,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                        ) {
                            Image(
                                painter = painterResource(id = item.image),
                                contentDescription = null,
                                modifier = modifier.size(140.dp)
                            )
                        }
                        Spacer(modifier = modifier.size(10.dp))
                        Text(
                            text = item.name,
//                        modifier = modifier.background(color = Color.White),
                            textAlign = TextAlign.Center,
                            modifier = modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FeaturedProfilePreview() {
    AlumniConnectTheme {
        FeaturedProfile()
    }
}