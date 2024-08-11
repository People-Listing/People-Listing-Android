package com.example.peoplelisting.ui.createuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.peoplelisting.R
import com.example.peoplelisting.data.resource.ResourceState
import com.example.peoplelisting.databinding.CreateUserFragmentBinding
import com.example.peoplelisting.internal.extensions.hide
import com.example.peoplelisting.internal.extensions.show
import com.example.peoplelisting.internal.extensions.startIgnoringTouchEvents
import com.example.peoplelisting.internal.extensions.stopIgnoringTouchEvents
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.listuser.ListUsersViewModel
import com.example.peoplelisting.ui.screens.createpeople.intent.CreatePersonIntent
import com.example.peoplelisting.ui.screens.createpeople.model.EntryType
import com.example.peoplelisting.ui.screens.createpeople.model.FormEntry
import com.example.peoplelisting.ui.screens.createpeople.state.CreatePersonUiState
import com.example.peoplelisting.ui.screens.createpeople.view.PersonForm
import com.example.peoplelisting.ui.screens.createpeople.view.RoundedButton
import com.example.peoplelisting.ui.snackbar.CustomSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.example.peoplelisting.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalArgumentException


class CreateUserFragment : BaseFragment() {
    override val screenTitle: String
        get() = getString(R.string.create_user_title)
    override val showBackButton: Boolean
        get() = true
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
                val listingViewModel = koinViewModel<ListUsersViewModel>(viewModelStoreOwner = requireActivity())
                val viewModel = koinViewModel<CreateUserViewModel>()
                val uiState by viewModel.uiState.observeAsState()
                val entries by viewModel.entries.observeAsState()
                val isButtonEnabled: Boolean
                val alpha: Float
                val isLoading: Boolean
                when (uiState) {
                    is CreatePersonUiState.Normal -> {
                        (requireActivity() as AppCompatActivity).stopIgnoringTouchEvents()
                        isButtonEnabled = (uiState as CreatePersonUiState.Normal).isButtonEnabled
                        alpha = if (isButtonEnabled) 1.0f else 0.5f
                        isLoading = false
                    }

                    is CreatePersonUiState.Loading -> {
                        (requireActivity() as AppCompatActivity).startIgnoringTouchEvents()
                        isButtonEnabled = false
                        isLoading = true
                        alpha = 1.0f
                    }

                    is CreatePersonUiState.Success -> {
                        (requireActivity() as AppCompatActivity).stopIgnoringTouchEvents()
                        isButtonEnabled = false
                        alpha = 0.5f
                        isLoading = false
                        listingViewModel.setFreshlyCreatedUser((uiState as CreatePersonUiState.Success).person)
                        navManager.navigateUp()
                    }

                    else -> {
                        throw IllegalArgumentException()
                    }
                }
                AppTheme {
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
                }
            }
        }
        return composeView
    }
}