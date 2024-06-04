package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.screens.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.screens.listpeople.state.PeopleListingUiState
import com.example.peoplelisting.ui.screens.listpeople.view.ListUserScreen
import com.example.peoplelisting.ui.screens.listpeople.view.ShimmeringList
import com.example.peoplelisting.ui.snackbar.ComposeSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.example.peoplelisting.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class ListUsersFragment : BaseFragment() {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    override val showBackButton: Boolean
        get() = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val composeView = ComposeView(requireContext())
        composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                val scope = rememberCoroutineScope()
                val vm: ListUsersViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = requireActivity())
                LaunchedEffect(key1 = Unit) {
                    vm.handleIntent(PeopleListingViewIntent.LoadData)
                }
                val errorState = vm.errorState.observeAsState(initial = null).value
                val state = vm.uiState.observeAsState(initial = PeopleListingUiState.LOADING).value
                AppTheme {
                    Box {
                        when (state) {
                            PeopleListingUiState.LOADING -> ShimmeringList()
                            is PeopleListingUiState.REFRESHING -> {
                                val people = state.people
                                ListUserScreen(
                                    people = people,
                                    isRefreshing = true,
                                    onRefresh = {},
                                    onClick = ::navigateToAddPersonScreen
                                )
                            }

                            is PeopleListingUiState.NORMAL -> {
                                val people = state.people
                                ListUserScreen(
                                    people = people,
                                    isRefreshing = false,
                                    onRefresh = {
                                        scope.launch {
                                            vm.handleIntent(PeopleListingViewIntent.RefreshData)
                                        }
                                    },
                                    onClick = ::navigateToAddPersonScreen
                                )
                            }
                        }
                        errorState?.apply {
                            val duration = if (state is PeopleListingUiState.NORMAL) {
                                10_000L
                            } else {
                                null
                            }
                            ComposeSnackBar(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                snackBarData = SnackBarData(
                                    stringResource(id = this.errorMessage), duration = duration, snackBarButtonData =
                                    SnackBarButtonData(listener = {
                                        if (state is PeopleListingUiState.NORMAL) {
                                            vm.handleIntent(PeopleListingViewIntent.RefreshData)
                                        } else {
                                            vm.handleIntent(PeopleListingViewIntent.LoadData)
                                        }
                                    })
                                )
                            )
                        }
                    }
                }
            }
        }
        return composeView
    }

    private fun navigateToAddPersonScreen() {
        navManager.navigateToDirection(
            ListUsersFragmentDirections.actionListUsersFragmentToCreateUserFragment(),
            enterAnim = R.anim.enter_from_right,
            exitAnim = R.anim.exit_to_left,
            popEnterAnim = R.anim.enter_from_left,
            popExitAnim = R.anim.exit_to_right
        )
    }
}