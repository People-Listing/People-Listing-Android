package com.example.peoplelisting.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.peoplelisting.internal.managers.NavigationManager
import com.example.peoplelisting.ui.main.MainViewModel

abstract class BaseFragment : Fragment /*, DIAware*/ {

    constructor() : super()
    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    //override val di: DI by closestDI()
    abstract val screenTitle: String
    abstract val showBackButton: Boolean
    private val mainViewModel: MainViewModel by viewModels(ownerProducer = { requireActivity() })
    protected lateinit var navManager: NavigationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navManager = NavigationManager(findNavController())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        setScreenTitle()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setScreenTitle() {
        mainViewModel.setScreenTitle(screenTitle)
    }
}