package com.example.peoplelisting.data.di

import com.example.peoplelisting.data.network.retrofit.Retrofit
import com.example.peoplelisting.data.network.service.PeopleService
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitDi {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Retrofit.loggingInterceptor)
            .callTimeout(Retrofit.API_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : retrofit2.Retrofit {
       return retrofit2.Retrofit.Builder()
            .baseUrl(Retrofit.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providePeopleService(retrofit: retrofit2.Retrofit): PeopleService {
        return retrofit.create(PeopleService::class.java)
    }

}