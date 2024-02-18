package com.example.alumniconnect.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations


data class Domain(val name: String, val image: Int)

@Composable
fun DomainsSection(modifier: Modifier = Modifier, navController: NavController) {
    val domainsList = listOf(
        Domain("Engineering", R.drawable.profile_pic),
        Domain("Design", R.drawable.login_img),
        Domain("Product Management", R.drawable.profile_pic),
        Domain("Data Science", R.drawable.profile_pic),
        Domain("Marketing", R.drawable.profile_pic),
        Domain("Sales", R.drawable.profile_pic),
        Domain("Finance", R.drawable.profile_pic),
        Domain("Human Resources", R.drawable.login_img),
        Domain("Legal", R.drawable.profile_pic),
        // Add more profiles as needed
    )
    val primaryGreyColor = colorResource(id = R.color.btn_outline)
    val btnTextColor = colorResource(id = R.color.outline_grey)

    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Find by Domains",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.padding(10.dp),
            fontWeight = FontWeight.Medium
        )
        LazyColumn(
            modifier = modifier.padding(10.dp)
        ) {
            itemsIndexed(domainsList) { index, item ->
                OutlinedIconButton(
                    onClick = { navController.navigate("${AlumniConnectNavDestinations.AlumniDirectory.route}/${item.name}") },
                    modifier = modifier
                        .fillMaxSize()
                        .height(90.dp)
                        .padding(vertical = 5.dp),
                    border = BorderStroke(width = 1.dp, color = primaryGreyColor),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                    ) {
                        Text(text = item.name, fontWeight = FontWeight.Medium)
                        Spacer(modifier = modifier.size(5.dp))
                        Text(text = "100+ alumni", color = btnTextColor)
                    }
                }
            }
        }
    }

}