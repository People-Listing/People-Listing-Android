package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.NetworkResponse
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.screens.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.screens.listpeople.state.PeopleListingUiState
import com.example.peoplelisting.ui.screens.listpeople.view.ShimmeringList
import com.example.peoplelisting.ui.screens.listpeople.view.Users
import com.example.peoplelisting.ui.snackbar.ComposeSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.example.peoplelisting.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

class ListUsersFragment : BaseFragment() {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    override val showBackButton: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                val vm: ListUsersViewModel =
                    koinViewModel<ListUsersViewModel>(viewModelStoreOwner = requireActivity())
                LaunchedEffect(key1 = Unit) {
                    if (savedInstanceState == null)
                        vm.handleIntent(PeopleListingViewIntent.LoadData)
                }
                val errorState by vm.errorState.observeAsState(initial = null)
                val state by vm.uiState.observeAsState(initial = PeopleListingUiState.FETCHING)
                AppTheme {
                    Box {
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
                                        if (!isRefreshing) vm.handleIntent(PeopleListingViewIntent.RefreshData)
                                    }, onClick = {}
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

}