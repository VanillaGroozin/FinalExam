package com.example.finalexam.domain.usecase

import android.content.Context
import androidx.room.Room
import com.example.finalexam.data.WeatherAppDatabase
import kz.step.weatherapp.data.City
import kz.step.weatherapp.data.WeatherInCity

class DatabaseUseCase {
    // я в итоге это не использовал, потому что не успеваю
    var weatherAppDatabase: WeatherAppDatabase

    constructor(context: Context) {
        weatherAppDatabase = Room.databaseBuilder(
            context,
            WeatherAppDatabase::class.java,
            "WeatherAppDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun initiateInsertCity(city: City) {
        weatherAppDatabase.getCityDao().initiateInsertCity(city)
    }

    fun initiateInsertCities(cities: List<City>) {
        weatherAppDatabase.getCityDao().initiateInsertCities(cities)
    }

    fun initiateGetCities(): List<City> {
        return weatherAppDatabase.getCityDao().initiateGetCities()
    }

    fun initiateGetCityById(id: Int): City {
        return weatherAppDatabase.getCityDao().initiateGetCityById(id)
    }

    fun initiateGetCityByName(name: String): City {
        return weatherAppDatabase.getCityDao().initiateGetCityByName(name)
    }

    fun initiateGetCityByNameAndCountry(name: String, country: String): City {
        return weatherAppDatabase.getCityDao().initiateGetCityByNameAndCountry(name,country)
    }

    fun initiateDeleteCityById(id: Int) {
        weatherAppDatabase.getCityDao().initiateDeleteCityById(id)
    }

    fun initiateDeleteCityByName(name: String) {
        weatherAppDatabase.getCityDao().initiateDeleteCityByName(name)
    }

    fun initiateGetCitiesByQuery(query: String): List<City> {
        return weatherAppDatabase.getCityDao().initiateGetCitiesByQuery(query)
    }

    fun initiateDeleteCityByNameAndCountry(name: String, country: String) {
        weatherAppDatabase.getCityDao().initiateDeleteCityByNameAndCountry(name,country)
    }

    fun initiateInsertCityInWeatherList(inCity: WeatherInCity) {
        weatherAppDatabase.getCityInWeatherListDao().initiateInsertCity(inCity)
    }

    fun initiateInsertCitiesInWeatherList(inCities: List<WeatherInCity>) {
        weatherAppDatabase.getCityInWeatherListDao().initiateInsertCities(inCities)
    }

    fun initiateGetCitiesInWeatherList(): List<WeatherInCity> {
        return weatherAppDatabase.getCityInWeatherListDao().initiateGetCities()
    }

    fun initiateGetCityInWeatherListByCityId(cityId: Long): WeatherInCity {
        return weatherAppDatabase.getCityInWeatherListDao().initiateGetCityByCityId(cityId)
    }

    fun initiateGetCityInWeatherListByNameAndCountry(name: String, country: String): WeatherInCity {
        return weatherAppDatabase.getCityInWeatherListDao().initiateGetCityByName(name)
    }

    fun initiateUpdateCityInWeatherListWeather(weatherInCity: WeatherInCity) {
        weatherAppDatabase.getCityInWeatherListDao().initiateUpdateCityWeather(weatherInCity)
    }

    fun initiateDeleteCityInWeatherListByCityId(cityId: Long) {
        weatherAppDatabase.getCityInWeatherListDao().initiateDeleteCityByCityId(cityId)
    }

    fun initiateDeleteCityInWeatherListByNameAndCountry(name: String, country: String) {
        weatherAppDatabase.getCityInWeatherListDao().initiateDeleteCityByNameAndCountry(name,country)
    }
}