package com.example.peoplelisting.data.di

import com.example.peoplelisting.data.network.retrofit.Retrofit
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.repository.impl.PeopleRepositoryImpl
import com.example.peoplelisting.ui.createpeople.view.CreateUserViewModel
import com.example.peoplelisting.ui.listpeople.view.ListUsersViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ListUsersViewModel(get()) }
    factory { CreateUserViewModel(get()) }

}

val serviceModule = module {
    factory { Retrofit.retrofit.create(PeopleService::class.java) }

}

val repoModule = module {
    single<PeopleRepository> { PeopleRepositoryImpl(get()) }

}