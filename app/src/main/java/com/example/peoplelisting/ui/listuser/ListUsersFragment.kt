package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.resource.ResourceState
import com.example.peoplelisting.databinding.ListUsersFragmentBinding
import com.example.peoplelisting.internal.CustomTypefaceSpan
import com.example.peoplelisting.internal.extensions.hide
import com.example.peoplelisting.internal.extensions.show
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.internal.utilities.getFont
import com.example.peoplelisting.ui.base.BaseFragment
import com.example.peoplelisting.ui.snackbar.CustomSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import org.kodein.di.android.x.viewmodel.viewModel
import timber.log.Timber

class ListUsersFragment : BaseFragment(R.layout.list_users_fragment) {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    override val showBackButton: Boolean
        get() = false
    private val binding: ListUsersFragmentBinding by viewBinding(ListUsersFragmentBinding::bind)
    private val viewModel: ListUsersViewModel by viewModel(ownerProducer = { requireActivity() })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        manageSubscription()
        manageEvents()
        Timber.tag("getMyPeople").i("onViewCreated")
        if(savedInstanceState == null)
            viewModel.getMyPeople(checkIfFetched = true)

    }

    private fun startLoading() {
        binding.emptyResult.hide()
        binding.fab.hide()
        binding.totalCount.hide()
        (binding.people.adapter as PeopleListingAdapter).submitList(List(5) {
            PersonDto().apply { isLoading = true }
        })
    }

    private fun stopLoading() {
        binding.emptyResult.hide()
        binding.fab.show()
        binding.totalCount.show()
        if(binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setEmptyResult() {
        binding.totalCount.hide()
        binding.people.hide()
        binding.fab.hide()
        binding.emptyResult.show()
    }

    private fun manageEvents() {
        binding.fab.setOnClickListener {
            navManager.navigateToDirection(ListUsersFragmentDirections.actionListUsersFragmentToCreateUserFragment())
        }
        binding.swipeRefresh.refreshes().subscribe{
            viewModel.getMyPeople()
        }.isDisposed
    }

    private fun manageSubscription() {
        viewModel.usersResponse.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.resourceState) {
                ResourceState.LOADING -> {
                    Timber.tag("getMyPeople").i("laoding")
                    startLoading()
                }

                ResourceState.SUCCESS -> {
                    if(it.data?.isEmpty() == true) {
                        setEmptyResult()
                    } else {
                        stopLoading()
                        (binding.people.adapter as PeopleListingAdapter).submitList(it.data)
                        setTotalCount(it.data?.count() ?: 0)
                    }
                }

                ResourceState.ERROR -> {
                    if(binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    val message = it.message ?: getString(R.string.get_people_error)
                    val snackBarData = SnackBarData(message, snackBarButtonData =  SnackBarButtonData(listener = {
                        viewModel.getMyPeople()
                    }))
                    CustomSnackBar(viewLifecycleOwner).showSnackBar(requireView(), snackBarData)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.people.layoutManager = LinearLayoutManager(requireContext())
        val adapter = PeopleListingAdapter()
        binding.people.adapter = adapter
    }

    private fun setTotalCount(totalCount: Int) {
        val dinBold = CustomTypefaceSpan("din", getFont(R.font.din_bold)!!)
        val completeText = getString(R.string.total_count, totalCount.toString())
        val toBeBold = totalCount.toString()
        val spannableString = SpannableString(completeText)
        val start = completeText.indexOf(toBeBold)
        val end = start + toBeBold.length
        spannableString.setSpan(dinBold, start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        binding.totalCount.text = spannableString

    }
}