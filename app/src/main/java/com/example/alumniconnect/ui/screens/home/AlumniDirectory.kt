package com.example.alumniconnect.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alumniconnect.R
import com.example.alumniconnect.ui.AppViewModelProvider
import com.example.alumniconnect.ui.navigation.AlumniConnectNavDestinations
import com.example.alumniconnect.ui.screens.profile.ProfileViewModel
import com.example.alumniconnect.ui.theme.AlumniConnectTheme

@Composable
fun AlumniDirectoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    domainId: String,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by homeViewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                screenTitle = "Alumni Directory",
                canGoBack = true,
                onNavClick = { navController.popBackStack() })
        },
        bottomBar = { BottomNavBar(navController = navController) })

    { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppSearchBar(
                domainId = domainId,
                homeViewModel = homeViewModel,
                uiState = uiState,
                navController = navController
            )
            Spacer(modifier = modifier.size(10.dp))
            AlumniList(navController = navController, uiState = uiState, domainId = domainId)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    domainId: String,
    homeViewModel: HomeViewModel,
    uiState: HomeUiState,
    navController: NavController
) {
    val placeHolderGrey = colorResource(id = R.color.secondary_grey)
    SearchBar(
        query = uiState.searchText,
        onQueryChange = { homeViewModel.onSearchTextChange(it) },
        onSearch = {},
        active = uiState.isSearching,
        onActiveChange = { homeViewModel.onToggleSearch() },
        placeholder = { Text(text = "Search ${domainId.lowercase()} alumni") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = placeHolderGrey
            )
        },
        shape = RoundedCornerShape(15.dp),
        colors = SearchBarDefaults.colors(
            inputFieldColors = TextFieldDefaults.colors(
                unfocusedPlaceholderColor = placeHolderGrey,
                focusedPlaceholderColor = placeHolderGrey,
                unfocusedTextColor = placeHolderGrey
            ),
            containerColor = Color(0xFFF5F5F5)
        ),
        modifier = modifier.padding(top = 0.dp),
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
    ) {
        LazyColumn() {
            itemsIndexed(uiState.currentList.filter { it.domain == domainId }) { index, item ->
                AlumniTile(
                    alumniProfile = item,
                    onProfileClick = { navController.navigate("${AlumniConnectNavDestinations.AlumniProfile.route}/${item.id}") })
            }
        }
    }
}


@Composable
fun AlumniList(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: HomeUiState,
    domainId: String
) {
    LazyColumn() {
        itemsIndexed(uiState.currentList.filter { it.domain == domainId }) { index, item ->
            AlumniTile(
                alumniProfile = item,
                onProfileClick = { navController.navigate("${AlumniConnectNavDestinations.AlumniProfile.route}/${item.id}") })
        }
    }
}


@Composable
fun AlumniTile(
    modifier: Modifier = Modifier,
    alumniProfile: UserProfile,
    onProfileClick: () -> Unit = {}
) {
    val fontGrey = colorResource(id = R.color.secondary_grey)

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable { onProfileClick() }
    ) {
        Image(
            painter = painterResource(id = alumniProfile.profilePic),
            contentDescription = null,
            modifier = modifier
                .size(70.dp)
                .clip(CircleShape),
        )
        Column(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = alumniProfile.firstName + " " + alumniProfile.lastName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                modifier = modifier.padding(top = 10.dp)
            )
            Text(
                text = alumniProfile.role,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                color = fontGrey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumniDirectoryPreview() {
    val navController = rememberNavController()
    AlumniConnectTheme {
        AlumniDirectoryScreen(navController = navController, domainId = "Engineering")
    }
}