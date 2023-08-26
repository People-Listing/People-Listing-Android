package com.example.peoplelisting.data.repository

import com.example.peoplelisting.data.model.api.Person
import retrofit2.Response

interface PeopleRepository {
    suspend fun getUsers(): Response<List<Person>>
}