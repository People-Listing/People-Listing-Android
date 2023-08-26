package com.example.peoplelisting.data.repository.impl

import com.example.peoplelisting.data.model.api.Person
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import retrofit2.Response

class PeopleRepositoryImpl(private val service: PeopleService): PeopleRepository {
    override suspend fun getUsers(): Response<List<Person>> {
       return service.getPeople()
    }
}