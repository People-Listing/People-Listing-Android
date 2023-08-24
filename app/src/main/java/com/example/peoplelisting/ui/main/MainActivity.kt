package com.example.peoplelisting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.ActivityMainBinding
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.internal.managers.NavigationManager

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}