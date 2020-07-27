package com.example.finalexam.data.api

import WeatherResponse
import androidx.annotation.NonNull
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiImplementation : WeatherService {

    var api: WeatherService = ApiConnection().initializeAPI()
    constructor()

    override fun getCurrentWeatherData(
        lat: String?,
        lon: String?,
        app_id: String?
    ): Call<WeatherResponse> {
        return api.getCurrentWeatherData(lat, lon, app_id)
    }
}