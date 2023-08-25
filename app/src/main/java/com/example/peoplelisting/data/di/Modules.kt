package com.example.peoplelisting.data.di

import com.example.peoplelisting.ui.main.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val viewModelModule = DI.Module("viewModel") {
    bindProvider { MainViewModel() }

}

val serviceModule = DI.Module("service") {

}

val repoModule = DI.Module("repository") {

}