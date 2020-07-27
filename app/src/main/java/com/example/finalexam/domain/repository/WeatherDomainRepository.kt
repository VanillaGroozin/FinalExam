package com.example.finalexam.domain.repository

import WeatherResponse
import io.reactivex.Observable
import retrofit2.Call

interface WeatherDomainRepository {
    fun initiateGetWeather(): WeatherResponse?
}
