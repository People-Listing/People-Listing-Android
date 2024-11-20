package com.example.peoplelisting.data.model.api

import com.example.peoplelisting.data.model.dto.PersonDto
import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id")
    val userId: String? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("profession")
    val profession: String? = null,
) {
    fun toPersonDto(): PersonDto {
        val nameSeparator = " "
        val firstName = firstName ?: ""
        val lastName = lastName ?: ""
        val fullName = listOf(firstName, lastName).joinToString(nameSeparator)
        return PersonDto(
            name = fullName,
            age = age ?: -1,
            profession = profession ?: "",
            id = userId
        )
    }
}