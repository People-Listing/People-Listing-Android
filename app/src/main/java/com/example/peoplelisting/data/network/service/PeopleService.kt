package com.example.peoplelisting.data.network.service

import com.example.peoplelisting.data.model.api.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PeopleService {

    @Headers("Content-Type: application/json")
    @GET("getUsers")
    suspend fun getPeople(): Response<List<Person>>
}