package com.example.peoplelisting.ui.listuser

import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.ListUsersFragmentBinding
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.ui.base.BaseFragment

class ListUsersFragment: BaseFragment() {
    override val screenTitle: String
        get() = getString(R.string.list_user_title)
    private val binding: ListUsersFragmentBinding by viewBinding(ListUsersFragmentBinding::bind)
}