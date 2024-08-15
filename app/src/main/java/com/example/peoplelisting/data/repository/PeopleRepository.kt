package com.example.peoplelisting.data.repository

import com.example.peoplelisting.data.model.api.CreateUserBody
import com.example.peoplelisting.data.model.api.Person
import com.example.peoplelisting.data.network.adapters.GenericResponse
import retrofit2.Response

interface PeopleRepository {
    suspend fun getUsers(): GenericResponse<List<Person>>

    suspend fun createUser(firstName: String, lastName: String, age: Int, profession: String): GenericResponse<Person>
}