package com.example.peoplelisting.data.resource

import com.example.peoplelisting.data.network.NetworkResponse

data class Resource<out T>(
    val data: T? = null,
    val resourceState: ResourceState = ResourceState.LOADING,
    val failure: NetworkResponse.Failure<*>? = null
)