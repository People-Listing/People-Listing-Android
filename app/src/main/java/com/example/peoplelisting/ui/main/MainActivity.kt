package com.example.peoplelisting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.peoplelisting.databinding.ActivityMainBinding
import com.example.peoplelisting.internal.extensions.viewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.x.viewmodel.viewModel

class MainActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
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