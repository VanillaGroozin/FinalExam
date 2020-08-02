package com.example.finalexam.domain.repository

import com.example.finalexam.data.WeatherResponse
import io.reactivex.Observable

interface WeatherDomainRepository {
    fun initiateGetWeather(city: String) : Observable<WeatherResponse>
}
