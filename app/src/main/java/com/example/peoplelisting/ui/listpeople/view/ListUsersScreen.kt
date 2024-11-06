package com.example.peoplelisting.ui.listpeople.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.peoplelisting.R
import com.example.peoplelisting.internal.handlers.AppErrorHandler
import com.example.peoplelisting.ui.listpeople.components.ShimmeringList
import com.example.peoplelisting.ui.listpeople.components.Users
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListUsersScreen(modifier: Modifier = Modifier, navigateToCreate: () -> Unit) {
    val activity = LocalContext.current as ComponentActivity
    val listingViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = activity)
    val effect by listingViewModel.effect.collectAsState(initial = null)
    val state by listingViewModel.uiState.observeAsState()
    LaunchedEffect(key1 = Unit) {
        listingViewModel.handleEvent(PeopleListingUiEvent.LoadData)
    }
    Box(modifier = modifier) {
        HandleState(state = state, onCreateClick = {
            listingViewModel.handleEvent(PeopleListingUiEvent.OnCreateClicked)
        }, onRefresh = {
            if (state?.isRefreshing == false) listingViewModel.handleEvent(PeopleListingUiEvent.RefreshData)
        })
        HandleEffect(
            modifier = Modifier.align(Alignment.BottomCenter),
            effect = effect,
            navigateToCreate = navigateToCreate
        )

    }
}

@Composable
fun HandleEffect(
    modifier: Modifier = Modifier,
    navigateToCreate: () -> Unit,
    effect: PeopleListingEffect?
) {
    effect?.apply {
        when (this) {
            is PeopleListingEffect.ShowError -> {
                AppErrorHandler(
                    modifier = modifier,
                    failure = failure,
                    errorMessage = stringResource(id = R.string.get_people_error),
                    duration = duration,
                    tryAgain = tryAgain
                )
            }

            PeopleListingEffect.NavigateToCreate -> {
                navigateToCreate()
            }
        }
    }
}

@Composable
fun HandleState(
    state: PeopleListingUiState?,
    onCreateClick: () -> Unit,
    onRefresh: () -> Unit
) {
    state?.apply {
        if (isLoading) {
            ShimmeringList()
        } else {
            Users(
                people = people,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                onClick = onCreateClick
            )
        }
    }
}