package com.example.peoplelisting.data.repository.impl

import com.example.peoplelisting.data.model.api.CreateUserBody
import com.example.peoplelisting.data.model.api.Person
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import retrofit2.Response
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(private val service: PeopleService) : PeopleRepository {
    override suspend fun getUsers(): Response<List<Person>> {
        return service.getPeople()
    }

    override suspend fun createUser(
        firstName: String,
        lastName: String,
        age: Int,
        profession: String
    ): Response<Person> {
        val body = CreateUserBody(firstName, lastName, age, profession)
        return service.createUser(body)
    }

}