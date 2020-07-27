package com.example.finalexam.domain.usecase

import WeatherResponse
import com.example.finalexam.data.repository.WeatherRepository
import com.example.finalexam.domain.repository.WeatherDomainRepository
import retrofit2.Call


class GetWeatherUseCase {

    private var weatherDomainRepository: WeatherDomainRepository = WeatherRepository()

    constructor()

    fun getWeatherResponse(): WeatherResponse? {
        return weatherDomainRepository.initiateGetWeather()
    }
}