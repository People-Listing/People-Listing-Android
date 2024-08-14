package com.example.peoplelisting.ui.screens.createpeople.view

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.NetworkResponse
import com.example.peoplelisting.internal.extensions.startIgnoringTouchEvents
import com.example.peoplelisting.internal.extensions.stopIgnoringTouchEvents
import com.example.peoplelisting.internal.managers.NavigationManager
import com.example.peoplelisting.ui.createuser.CreateUserViewModel
import com.example.peoplelisting.ui.listuser.ListUsersViewModel
import com.example.peoplelisting.ui.screens.createpeople.intent.CreatePersonIntent
import com.example.peoplelisting.ui.screens.createpeople.state.CreatePersonUiState
import com.example.peoplelisting.ui.snackbar.ComposeSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.example.peoplelisting.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.lang.IllegalArgumentException

@Composable
fun CreatePersonScreen(modifier: Modifier = Modifier, navController: NavController) {
    val activity = LocalContext.current as ComponentActivity
    val navManager = NavigationManager(navController = navController)
    val listingViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = activity)
    val viewModel = koinViewModel<CreateUserViewModel>()
    val uiState by viewModel.uiState.observeAsState()
    val entries by viewModel.entries.observeAsState()
    val errorState by viewModel.errorState.observeAsState()
    val isButtonEnabled: Boolean
    val alpha: Float
    val isLoading: Boolean
    when (uiState) {
        is CreatePersonUiState.Normal -> {
            isButtonEnabled =
                (uiState as CreatePersonUiState.Normal).isButtonEnabled
            alpha = if (isButtonEnabled) 1.0f else 0.5f
            isLoading = false
        }

        is CreatePersonUiState.Loading -> {
            activity.startIgnoringTouchEvents()
            isButtonEnabled = false
            isLoading = true
            alpha = 1.0f
        }

        is CreatePersonUiState.Success -> {
            activity.stopIgnoringTouchEvents()
            isButtonEnabled = false
            alpha = 0.5f
            isLoading = false
            LaunchedEffect(key1 = Unit) {
                listingViewModel.setFreshlyCreatedUser((uiState as CreatePersonUiState.Success).person)
                navManager.navigateUp()
            }
        }

        else -> {
            throw IllegalArgumentException()
        }
    }
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp),
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            PersonForm(entries = entries ?: listOf()) { value, type ->
                viewModel.handleIntent(CreatePersonIntent.SetEntry(type, value))
            }
            RoundedButton(
                modifier = Modifier.padding(bottom = 48.dp),
                onClick = { viewModel.handleIntent(CreatePersonIntent.CreatePerson) },
                isEnabled = isButtonEnabled,
                alpha = alpha,
                isLoading = isLoading
            )
        }
        errorState?.apply {
            activity.stopIgnoringTouchEvents()
            val errorMessage = when (errorState) {
                is NetworkResponse.NetworkError -> R.string.no_internet
                else -> R.string.create_people_error
            }

            ComposeSnackBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                snackBarData = SnackBarData(
                    stringResource(id = errorMessage),
                    duration = 10_000
                )
            )
        }
    }

}