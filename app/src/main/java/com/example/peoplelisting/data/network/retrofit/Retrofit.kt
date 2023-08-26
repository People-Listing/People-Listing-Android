package com.example.peoplelisting.data.network.retrofit

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object Retrofit {

    private const val API_TIME_OUT = 60L

    private const val BASE_URL = "https://192.168.43.18:3002/"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

}