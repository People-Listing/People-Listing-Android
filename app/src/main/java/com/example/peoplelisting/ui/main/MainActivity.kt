package com.example.peoplelisting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.ActivityMainBinding
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.internal.managers.NavigationManager
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.provider

class MainActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModel()
    private lateinit var navigationManager: NavigationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        val navigationController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as
                NavHostFragment).navController
        navigationManager = NavigationManager(navigationController)
        binding.toolBar.setNavigationOnClickListener {
            navigationManager.navigateUp()
        }
        manageSubscription()
    }

    private fun manageSubscription() {
        viewModel.screenTitle.observe(this) {
            if(it == null) return@observe
            setScreenTitle(it)
        }
    }

    fun setScreenTitle(title: String) {
        binding.toolBar.title = title
    }
}