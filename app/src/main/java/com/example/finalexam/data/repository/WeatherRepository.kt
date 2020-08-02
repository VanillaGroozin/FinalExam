package com.example.finalexam.data.repository


import com.example.finalexam.data.WeatherResponse
import com.example.finalexam.data.api.ApiImplementation
import com.example.finalexam.domain.repository.WeatherDomainRepository
import io.reactivex.Observable
import java.lang.Exception


class WeatherRepository : WeatherDomainRepository {
    private var apiImplementation: ApiImplementation = ApiImplementation()

    constructor()


    override fun initiateGetWeather(city: String): Observable<WeatherResponse> {
        return apiImplementation.getCurrentWeatherData(city)
            .map { response ->
                if(response.isSuccessful) {
                    response.body()
                } else {
                    throw Exception()
                }
            }
    }
}



