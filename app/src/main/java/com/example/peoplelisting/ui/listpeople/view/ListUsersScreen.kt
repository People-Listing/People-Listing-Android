package com.example.peoplelisting.ui.listpeople.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.NetworkResponse
import com.example.peoplelisting.internal.managers.NavigationManager
import com.example.peoplelisting.ui.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.listpeople.state.PeopleListingUiState
import com.example.peoplelisting.ui.main.PeopleListingScreenRoute
import com.example.peoplelisting.ui.snackbar.ComposeSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListUsersScreen(modifier: Modifier = Modifier, navController: NavController) {
    val activity = LocalContext.current as ComponentActivity
    val navManager = NavigationManager(navController)
    val listingViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = activity)
    val errorState by listingViewModel.errorState.observeAsState(initial = null)
    val state by listingViewModel.uiState.observeAsState(initial = PeopleListingUiState.FETCHING)
    LaunchedEffect(key1 = Unit) {
        listingViewModel.handleIntent(PeopleListingViewIntent.LoadData)
    }
    Box(modifier = modifier) {
        when (state) {
            PeopleListingUiState.FETCHING -> ShimmeringList()
            is PeopleListingUiState.NORMAL -> {
                val people = (state as PeopleListingUiState.NORMAL).people
                val isRefreshing =
                    (state as PeopleListingUiState.NORMAL).isRefreshing
                Users(
                    people = people,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        if (!isRefreshing) listingViewModel.handleIntent(PeopleListingViewIntent.RefreshData)
                    }, onClick = {
                        navManager.navigateToRoute(
                            PeopleListingScreenRoute.Create.name,
                            popUpTo = null,
                            popUpInclusive = false,
                            enterAnim = R.anim.enter_from_right,
                            exitAnim = R.anim.exit_to_left,
                            popEnterAnim = R.anim.enter_from_left,
                            popExitAnim = R.anim.exit_to_right
                        )
                    }
                )
            }
        }
        errorState?.apply {
            val duration = if (state is PeopleListingUiState.NORMAL) {
                10_000L
            } else {
                null
            }
            val errorMessage = when (errorState) {
                is NetworkResponse.NetworkError -> R.string.no_internet
                else -> R.string.get_people_error

            }
            ComposeSnackBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                snackBarData = SnackBarData(
                    stringResource(id = errorMessage),
                    duration = duration,
                    snackBarButtonData =
                    SnackBarButtonData(listener = {
                        if (state is PeopleListingUiState.NORMAL) {
                            listingViewModel.handleIntent(PeopleListingViewIntent.RefreshData)
                        } else {
                            listingViewModel.handleIntent(PeopleListingViewIntent.LoadData)
                        }
                    })
                )
            )
        }

    }
}