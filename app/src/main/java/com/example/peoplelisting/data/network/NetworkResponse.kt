package com.example.peoplelisting.data.network

import java.io.IOException

sealed class NetworkResponse<out T, out U> {
    data class Success<out T>(val body: T) : NetworkResponse<T, Nothing>()
    sealed class Failure<out U> : NetworkResponse<Nothing, U>()
        data class ApiError<U>(val body: U, val code: Int) : Failure<U>()
        data class NetworkError<U>(val error: IOException) : Failure<U>()
        data class UnknownError<U>(val error: Throwable) : Failure<U>()

    fun getHttpCode(): Int? {
        if (this is ApiError) {
            return this.code
        }
        return null
    }
}