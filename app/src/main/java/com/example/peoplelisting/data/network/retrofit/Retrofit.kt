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

    const val API_TIME_OUT = 60L

    const val BASE_URL = "http://10.0.0.10:3002/"
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
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

}