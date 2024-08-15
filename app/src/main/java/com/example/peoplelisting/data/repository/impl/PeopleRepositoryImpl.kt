package com.example.peoplelisting.data.repository.impl

import com.example.peoplelisting.data.model.api.CreateUserBody
import com.example.peoplelisting.data.model.api.Person
import com.example.peoplelisting.data.network.adapters.GenericResponse
import com.example.peoplelisting.data.network.service.PeopleService
import com.example.peoplelisting.data.repository.PeopleRepository
import retrofit2.Response

class PeopleRepositoryImpl(private val service: PeopleService) : PeopleRepository {
    override suspend fun getUsers(): GenericResponse<List<Person>> {
        return service.getPeople()
    }

    override suspend fun createUser(
        firstName: String,
        lastName: String,
        age: Int,
        profession: String
    ): GenericResponse<Person> {
        val body = CreateUserBody(firstName, lastName, age, profession)
        return service.createUser(body)
    }

}