package com.example.peoplelisting.ui.createpeople.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.peoplelisting.R
import com.example.peoplelisting.internal.extensions.startIgnoringTouchEvents
import com.example.peoplelisting.internal.extensions.stopIgnoringTouchEvents
import com.example.peoplelisting.internal.handlers.AppErrorHandler
import com.example.peoplelisting.ui.createpeople.components.PersonForm
import com.example.peoplelisting.ui.createpeople.components.RoundedButton
import com.example.peoplelisting.ui.createpeople.model.EntryType
import com.example.peoplelisting.ui.listpeople.view.ListUsersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatePersonScreen(modifier: Modifier = Modifier, navigateUp: () -> Unit) {
    val activity = LocalContext.current as ComponentActivity
    val listingViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = activity)
    val viewModel = koinViewModel<CreateUserViewModel>()
    val uiState by viewModel.uiState.observeAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    Box(modifier = modifier) {
        HandleState(
            state = uiState,
            onCreateClicked = { viewModel.handleEvent(CreatePersonUiEvent.CreatePerson) },
            onInfoChanged = remember(viewModel) {
                { value, type ->
                    viewModel.handleEvent(CreatePersonUiEvent.SetEntry(type, value))
                }
            },
            onLoading = remember(activity) { { activity.startIgnoringTouchEvents() } }
        )
        HandleEffect(
            modifier = Modifier.align(Alignment.BottomCenter),
            effect = effect,
            onDone = remember(activity, listingViewModel) {
                {
                    activity.stopIgnoringTouchEvents()
                    listingViewModel.setFreshlyCreatedUser((effect as CreatePersonEffect.Done).person)
                    navigateUp()
                }
            },
            onError = remember(activity) {
                {
                    activity.stopIgnoringTouchEvents()
                }
            }
        )
    }

}

@Composable
fun HandleState(
    state: CreatePersonUiState?,
    onCreateClicked: () -> Unit,
    onInfoChanged: (String, EntryType) -> Unit,
    onLoading: () -> Unit
) {
    state?.apply {
        if (isLoading) {
            onLoading()
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp),
            modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            PersonForm(entries = state.entries, onInfoChanged = onInfoChanged)
            RoundedButton(
                modifier = Modifier.padding(bottom = 48.dp),
                onClick = onCreateClicked,
                isEnabled = isButtonEnabled,
                alpha = state.buttonAlpha,
                isLoading = isLoading
            )
        }
    }
}

@Composable
fun HandleEffect(
    modifier: Modifier = Modifier,
    effect: CreatePersonEffect?,
    onError: () -> Unit,
    onDone: () -> Unit
) {
    effect?.apply {
        if (effect is CreatePersonEffect.ShowError) {
            onError()
            AppErrorHandler(
                modifier = modifier,
                failure = effect.failure,
                errorMessage = stringResource(id = R.string.create_people_error),
                duration = effect.duration,
                tryAgain = null
            )
        } else if (effect is CreatePersonEffect.Done) {
            onDone()
        }
    }
}