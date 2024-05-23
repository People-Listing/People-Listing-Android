package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.screens.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.screens.listpeople.state.PeopleListingUiState
import com.example.peoplelisting.ui.screens.listpeople.view.ListUserScreen
import com.example.peoplelisting.ui.screens.listpeople.view.ShimmeringList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
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
                val vm: ListUsersViewModel = hiltViewModel(requireActivity())
                LaunchedEffect(key1 = Unit) {
                    vm.handleIntent(PeopleListingViewIntent.LoadData)
                }
                val state = vm.uiState.observeAsState(initial = PeopleListingUiState.LOADING).value
                Timber.tag("REFRESH").i("state: $state")
                when (state) {
                    PeopleListingUiState.LOADING -> ShimmeringList()
                    else -> {
                        Timber.tag("REFRESH").i("state: $state")
                        val people = when (state) {
                            is PeopleListingUiState.REFRESHING -> state.people
                            is PeopleListingUiState.NORMAL -> state.people
                            else -> listOf()
                        }
                        ListUserScreen(
                            people = people,
                            isRefreshing = state is PeopleListingUiState.REFRESHING,
                            onRefresh = {
                                if (state is PeopleListingUiState.NORMAL) {
                                    scope.launch {
                                        Timber.tag("REFRESH").i("refreshing api")
                                        vm.handleIntent(PeopleListingViewIntent.RefreshData)
                                    }
                                }
                            },
                            onClick = ::navigateToAddPersonScreen
                        )
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