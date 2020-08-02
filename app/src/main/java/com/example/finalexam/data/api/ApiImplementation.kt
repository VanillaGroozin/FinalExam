package com.example.finalexam.data.api

import com.example.finalexam.data.WeatherResponse
import io.reactivex.Observable
import retrofit2.Response

class ApiImplementation : WeatherService {

    private var api: WeatherService = ApiConnection().initializeAPI()
    constructor()


    override fun getCurrentWeatherData(
        city: String?,
        appid: String
    ): Observable<Response<WeatherResponse>> {
        return api.getCurrentWeatherData(city, appid)
    }
}