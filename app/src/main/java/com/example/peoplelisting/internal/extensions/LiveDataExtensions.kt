package com.example.peoplelisting.internal.extensions

import androidx.lifecycle.MutableLiveData
import com.example.peoplelisting.data.network.NetworkResponse
import com.example.peoplelisting.data.resource.Resource
import com.example.peoplelisting.data.resource.ResourceState

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T, message: String? = null) =
    postValue(
        Resource(
            data,
            ResourceState.SUCCESS
        )
    )

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(
        Resource(
            value?.data,
            ResourceState.LOADING
        )
    )

fun <T> MutableLiveData<Resource<T>>.setFailure(data: T? = null) =
    postValue(
        Resource(
            resourceState = ResourceState.ERROR,
            data = data ?: value?.data,
        )
    )