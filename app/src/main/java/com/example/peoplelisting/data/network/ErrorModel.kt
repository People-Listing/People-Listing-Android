package com.example.peoplelisting.data.network

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("responseCode", alternate = ["ResponseCode"])
    val responseCode: String? = null,
    @SerializedName("message", alternate = ["Message"])
    var message: String? = null
)
