package com.example.peoplelisting.ui.createuser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
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
import com.example.peoplelisting.ui.main.MainViewModel
import com.example.peoplelisting.ui.snackbar.CustomSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import org.kodein.di.android.x.viewmodel.viewModel

class CreateUserFragment : BaseFragment(R.layout.create_user_fragment) {
    override val screenTitle: String
        get() = getString(R.string.create_user_title)
    override val showBackButton: Boolean
        get() = true

    private val listingViewModel: ListUsersViewModel by activityViewModels()
    private val viewModel: CreateUserViewModel by viewModel()
    private val binding: CreateUserFragmentBinding by viewBinding(CreateUserFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageSubscription()
        manageEvents()
    }

    private fun manageEvents() {
        binding.etFirstName.textChanges().subscribe {
            viewModel.setFirstName(it.toString())
        }.isDisposed

        binding.etLastName.textChanges().subscribe {
            viewModel.setLastName(it.toString())
        }.isDisposed

        binding.etAge.textChanges().subscribe {
            viewModel.setAge(it.toString())
        }.isDisposed

        binding.etProfession.textChanges().subscribe {
            viewModel.setProfession(it.toString())
        }.isDisposed

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