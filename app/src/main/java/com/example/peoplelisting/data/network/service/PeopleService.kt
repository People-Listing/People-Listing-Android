package com.example.peoplelisting.data.network.service

import com.example.peoplelisting.data.model.api.CreateUserBody
import com.example.peoplelisting.data.model.api.Person
import com.example.peoplelisting.data.network.adapters.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface PeopleService {

    @GET("getUsers")
    suspend fun getPeople(): GenericResponse<List<Person>>

    @POST("addUser")
    suspend fun createUser(@Body createUserBody: CreateUserBody): GenericResponse<Person>
}