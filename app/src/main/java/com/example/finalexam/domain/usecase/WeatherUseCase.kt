package com.example.finalexam.domain.usecase

import com.example.finalexam.data.WeatherResponse
import com.example.finalexam.data.repository.WeatherRepository
import com.example.finalexam.domain.base.BaseNetworkUseCase
import io.reactivex.Observable
import kz.step.weatherapp.data.City
import kz.step.weatherapp.data.WeatherInCity


class WeatherUseCase: BaseNetworkUseCase<WeatherResponse> {
    private var weatherRepository: WeatherRepository = WeatherRepository()

    constructor()

    override fun initiateCreateObservableByQuery(query: String): Observable<WeatherResponse> {
        return weatherRepository.initiateGetWeather(query)
    }

    fun weatherResponseToCityInWeatherEntity(city: WeatherResponse): WeatherInCity {
        val cityInWeatherList = WeatherInCity()
        cityInWeatherList.name = city.name!!
        cityInWeatherList.country = city.sys?.country!!
        cityInWeatherList.cityId = city.id!!
        cityInWeatherList.weatherDescription = city.weather?.get(0)?.description
        cityInWeatherList.weatherIcon = city.weather?.get(0)?.icon
        cityInWeatherList.mainTemp = city.main?.temp
        cityInWeatherList.minTemp = city.main?.tempMin
        cityInWeatherList.maxTemp = city.main?.tempMax
        cityInWeatherList.mainPressure = city.main?.pressure
        cityInWeatherList.mainHumidity = city.main?.humidity
        cityInWeatherList.visibility = city.visibility
        cityInWeatherList.windSpeed = city.wind?.speed
        cityInWeatherList.windDeg = city.wind?.deg
        cityInWeatherList.rain1h = city.rain?._1h
        cityInWeatherList.cloudsAll = city.clouds?.all
        cityInWeatherList.snow1h = city.snow?._1h
        cityInWeatherList.sysSunrise = city.sys?.sunrise
        cityInWeatherList.sysSunset = city.sys?.sunset
        cityInWeatherList.updateDt = city.dt

        return cityInWeatherList
    }

    fun weatherResponseToCityEntity(city: WeatherResponse): City {
        val cityResponse = City()
        cityResponse.apiCityId = city.id!!
        cityResponse.country = city.sys?.country!!
        cityResponse.name = city.name!!

        return cityResponse
    }
}