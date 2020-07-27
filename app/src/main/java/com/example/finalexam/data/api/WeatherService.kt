package com.example.finalexam.data.api

import WeatherResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
//    @GET("latest")
//    fun initiateGetCurrencies() : Observable<Response<Weather>>
//
//    @GET("data/2.5/weather?")
//    fun getWeatherOfCity(@Query("q") city: String, @Query("appid") key: String): Call<Weather>

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("APPID") app_id: String?
    ): Call<WeatherResponse>


    //"https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
}