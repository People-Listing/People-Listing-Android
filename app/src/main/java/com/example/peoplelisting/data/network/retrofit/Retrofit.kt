package com.example.peoplelisting.data.network.retrofit

import com.example.peoplelisting.data.network.adapters.NetworkResponseAdapterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object Retrofit {

    const val API_TIME_OUT = 60L

    const val BASE_URL = "http://192.168.10.42:3002/"
    val loggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.d(message)
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            .build()

    val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .build()

}