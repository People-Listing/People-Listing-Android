package com.example.peoplelisting.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.ActivityMainBinding
import com.example.peoplelisting.internal.extensions.viewBinding
import com.example.peoplelisting.internal.managers.NavigationManager
import com.example.peoplelisting.ui.screens.main.PeopleListingApp
import com.example.peoplelisting.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PeopleListingApp()
            }
        }
    }

}