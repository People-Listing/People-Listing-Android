package com.example.peoplelisting.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.peoplelisting.internal.managers.NavigationManager
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class BaseFragment : Fragment(), DIAware {

    override val di: DI by closestDI()

    lateinit var navManager: NavigationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navManager = NavigationManager(findNavController())
    }
}