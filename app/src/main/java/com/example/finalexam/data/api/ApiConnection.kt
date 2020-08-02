package com.example.finalexam.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiConnection {

    private lateinit var retrofit: Retrofit

    constructor(){
        initializeRetrofit()
    }

    private fun initializeRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.weatherUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(initializeOkHttpClient())
            .build()
    }

    private fun initializeOkHttpClient() : OkHttpClient{
        return OkHttpClient
            .Builder()
            .addInterceptor(initializeHttpLogging())
            .build()
    }

    private fun initializeHttpLogging() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun initializeAPI(): WeatherService{
        return retrofit.create(WeatherService::class.java)
    }
}