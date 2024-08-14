package com.example.peoplelisting.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.createpeople.view.CreatePersonScreen
import com.example.peoplelisting.ui.listpeople.view.ListUsersScreen
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.gilroy


enum class PeopleListingScreenRoute(@StringRes val title: Int) {
    Listing(R.string.list_user_title),
    Create(R.string.create_user_title)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    currentRoute: PeopleListingScreenRoute,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = currentRoute.title), style = TextStyle(
                    fontFamily = gilroy,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ),
                color = AppColor.white
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = AppColor.colorPrimary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = AppColor.white
                    )
                }
            }
        }
    )

}

@Composable
fun PeopleListingApp(navController: NavHostController = rememberNavController()) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = PeopleListingScreenRoute.valueOf(
        currentBackStackEntry?.destination?.route ?: PeopleListingScreenRoute.Listing.name
    )
    val canNavigateBack = navController.previousBackStackEntry != null
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                canNavigateBack = canNavigateBack,
                currentRoute = currentRoute
            ) { navController.navigateUp() }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = PeopleListingScreenRoute.Listing.name
        ) {
            composable(
                route = PeopleListingScreenRoute.Listing.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(300)
                    )
                }
            ) {
                ListUsersScreen(modifier = Modifier.fillMaxSize().padding(top = 10.dp), navController)
            }
            composable(route = PeopleListingScreenRoute.Create.name,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(300)
                    )
                }) {
                CreatePersonScreen(
                    navController = navController,
                    modifier = Modifier.fillMaxSize().padding(top = 20.dp)
                )
            }
        }
    }
}