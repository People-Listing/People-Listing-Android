package com.example.peoplelisting.data.di

import com.example.peoplelisting.data.network.retrofit.Retrofit
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.repository.impl.PeopleRepositoryImpl
import com.example.peoplelisting.ui.createuser.CreateUserViewModel
import com.example.peoplelisting.ui.listuser.ListUsersViewModel
import com.example.peoplelisting.ui.main.MainViewModel
import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule, serviceModule, repoModule)
}

val viewModelModule = module {
    factory { MainViewModel() }
    factory { ListUsersViewModel(get()) }
    factory { CreateUserViewModel(get()) }

}

val serviceModule = module {
    factory { Retrofit.retrofit.create(PeopleService::class.java) }

}

val repoModule = module {
    single <PeopleRepository> { PeopleRepositoryImpl(get()) }

}