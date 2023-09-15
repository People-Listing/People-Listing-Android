package com.example.peoplelisting.data.di

import androidx.lifecycle.ViewModel
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.repository.impl.PeopleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PeopleRepoDi {
    @Binds
    abstract fun providePeopleRepo(peopleRepositoryImpl: PeopleRepositoryImpl): PeopleRepository
}