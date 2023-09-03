package com.example.peoplelisting.data.model.api

import com.google.gson.annotations.SerializedName

data class CreateUserBody (
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("profession")
    val profession: String,
)