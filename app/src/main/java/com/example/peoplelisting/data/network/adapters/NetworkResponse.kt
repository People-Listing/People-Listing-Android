package com.example.peoplelisting.data.network.adapters

import java.io.IOException

sealed interface NetworkResponse<out T : Any> {
    data class Success<T : Any>(val body: T?) : NetworkResponse<T>

    sealed interface Failure : NetworkResponse<Nothing> {
        /**
         * Failure response with body
         */
        data class ApiError<U : Any>(val body: U, val code: Int) : Failure

        /**
         * Unauthorized response without body
         */
        data object UnauthorizedError : Failure

        /**
         * Network error
         */
        data class NetworkError(val error: IOException) : Failure
        /**
         * For example, json parsing error
         */
        data class UnknownError(val error: Throwable?) : Failure

    }
}

typealias GenericResponse<S> = NetworkResponse<S>
