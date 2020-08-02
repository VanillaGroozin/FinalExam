package com.example.finalexam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.step.weatherapp.data.City
import kz.step.weatherapp.data.CityDao
import kz.step.weatherapp.data.WeatherInCity
import kz.step.weatherapp.data.WeatherInCityDao

@Database(entities = [City::class, WeatherInCity::class], version = 3)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getCityInWeatherListDao(): WeatherInCityDao
}