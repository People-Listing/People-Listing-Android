package com.example.peoplelisting.ui.base
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peoplelisting.internal.managers.NavigationManager
import com.example.peoplelisting.ui.main.MainActivity
import com.example.peoplelisting.ui.main.MainViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.android.x.viewmodel.viewModel

abstract class BaseFragment : Fragment, DIAware {

    constructor():super()
    constructor(@LayoutRes layoutRes: Int): super(layoutRes)
    override val di: DI by closestDI()
    abstract val screenTitle: String
    abstract val showBackButton: Boolean
    private val mainViewModel: MainViewModel by viewModel(ownerProducer = {requireActivity()})
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