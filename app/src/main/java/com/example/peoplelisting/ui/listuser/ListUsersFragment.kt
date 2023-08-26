package com.example.peoplelisting.ui.listuser

import android.os.Bundle
import android.text.SpannableString
import android.view.View
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
import org.kodein.di.android.x.viewmodel.viewModel

class ListUsersFragment : BaseFragment(R.layout.list_users_fragment) {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    private val binding: ListUsersFragmentBinding by viewBinding(ListUsersFragmentBinding::bind)
    private val viewModel: ListUsersViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        manageSubscription()
        viewModel.getMyPeople()

    }

    private fun setLoading() {
        binding.apiState.hide()
        binding.fab.hide()
        binding.totalCount.hide()
        (binding.people.adapter as PeopleListingAdapter).submitList(List(10) {
            PersonDto().apply { isLoading = true }
        })
    }

    private fun stopLoading() {
        binding.apiState.hide()
        binding.fab.show()
        binding.totalCount.show()
    }

    private fun manageSubscription() {
        viewModel.usersResponse.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.resourceState) {
                ResourceState.LOADING -> {
                    setLoading()
                }

                ResourceState.SUCCESS -> {
                    stopLoading()
                    if(it.data?.isEmpty() == true) {
                        binding.totalCount.hide()
                        binding.people.hide()
                        binding.fab.hide()
                        binding.apiState.text = "Nothing Found"
                        binding.apiState.show()
                    }
                    (binding.people.adapter as PeopleListingAdapter).submitList(it.data)
                    setTotalCount(it.data?.count() ?: 0)
                }

                ResourceState.ERROR -> {
//                    binding.totalCount.hide()
//                    binding.people.hide()
//                    binding.fab.hide()
//                    binding.apiState.text = "ERROR !!"
//                    binding.apiState.show()
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