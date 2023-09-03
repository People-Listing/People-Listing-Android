package com.example.peoplelisting.data.di

import com.example.peoplelisting.data.model.api.CreateUserBody
import com.example.peoplelisting.data.network.retrofit.Retrofit
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.repository.impl.PeopleRepositoryImpl
import com.example.peoplelisting.ui.createuser.CreateUserViewModel
import com.example.peoplelisting.ui.listuser.ListUsersViewModel
import com.example.peoplelisting.ui.main.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val viewModelModule = DI.Module("viewModel") {
    bindProvider { MainViewModel() }
    bindProvider { ListUsersViewModel(instance()) }
    bindProvider { CreateUserViewModel(instance()) }

}

val serviceModule = DI.Module("service") {
    bindProvider { Retrofit.retrofit.create(PeopleService::class.java) }

}

val repoModule = DI.Module("repository") {
    bindSingleton<PeopleRepository> { PeopleRepositoryImpl(instance()) }

}