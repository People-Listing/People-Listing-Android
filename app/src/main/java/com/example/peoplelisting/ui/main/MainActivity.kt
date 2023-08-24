package com.example.peoplelisting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.peoplelisting.databinding.ActivityMainBinding
import com.example.peoplelisting.internal.extensions.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}