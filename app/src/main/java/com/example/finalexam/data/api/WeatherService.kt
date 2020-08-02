package com.example.finalexam.data.api

import com.example.finalexam.data.WeatherResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData (
        @Query("q") city: String?,
        @Query("appid") appid: String = ApiConstants.apiKey
    ): Observable<Response<WeatherResponse>>

}