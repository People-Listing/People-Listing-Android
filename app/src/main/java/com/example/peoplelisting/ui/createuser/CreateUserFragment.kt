package com.example.peoplelisting.ui.createuser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
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
import com.example.peoplelisting.ui.screens.createpeople.model.EntryType
import com.example.peoplelisting.ui.screens.createpeople.model.FormEntry
import com.example.peoplelisting.ui.screens.createpeople.view.PersonForm
import com.example.peoplelisting.ui.snackbar.CustomSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.example.peoplelisting.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUserFragment : BaseFragment(R.layout.create_user_fragment) {
    override val screenTitle: String
        get() = getString(R.string.create_user_title)
    override val showBackButton: Boolean
        get() = true

    private val listingViewModel: ListUsersViewModel by activityViewModels()
    private val viewModel: CreateUserViewModel by viewModels()
    private val binding: CreateUserFragmentBinding by viewBinding(CreateUserFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageSubscription()
        manageEvents()
        binding.personForm.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    PersonForm(entries = viewModel.entriess) { value, type ->
                        when (type) {
                            EntryType.FirstName -> viewModel.setFirstName(value)
                            EntryType.LastName -> viewModel.setLastName(value)
                            EntryType.Age -> viewModel.setAge(value)
                            EntryType.Profession -> viewModel.setProfession(value)
                        }
                    }
                }
            }
        }
    }

    private fun manageEvents() {
        binding.createButton.root.setOnClickListener {
            viewModel.createUser()
        }
    }

    private fun manageSubscription() {
        viewModel.enableCreate.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            if (!it) {
                binding.createButton.root.alpha = 0.5f
            } else {
                binding.createButton.root.alpha = 1.0f
            }
            binding.createButton.root.isEnabled = it
        }

        viewModel.createUserResponse.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.resourceState) {
                ResourceState.LOADING -> {
                    (requireActivity() as AppCompatActivity).startIgnoringTouchEvents()
                    binding.createButton.buttonTitle.hide()
                    binding.createButton.progressBar.show()
                }

                ResourceState.SUCCESS -> {
                    (requireActivity() as AppCompatActivity).stopIgnoringTouchEvents()
                    binding.createButton.buttonTitle.show()
                    binding.createButton.progressBar.hide()
                    listingViewModel.setFreshlyCreatedUser(it.data!!)
                    navManager.navigateUp()
                }

                ResourceState.ERROR -> {
                    val message = it.message ?: getString(R.string.create_people_error)
                    val snackBarData = SnackBarData(message)
                    CustomSnackBar(viewLifecycleOwner).showSnackBar(requireView(), snackBarData)
                    (requireActivity() as AppCompatActivity).stopIgnoringTouchEvents()
                    binding.createButton.buttonTitle.show()
                    binding.createButton.progressBar.hide()
                }
            }
        }

    }
}