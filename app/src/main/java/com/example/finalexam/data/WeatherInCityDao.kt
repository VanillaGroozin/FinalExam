package kz.step.weatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherInCityDao {
    @Insert
    fun initiateInsertCity(inCity: WeatherInCity)

    @Insert
    fun initiateInsertCities(inCities: List<WeatherInCity>)

    @Query("SELECT * FROM weatherincity")
    fun initiateGetCities(): List<WeatherInCity>

    @Query("SELECT * FROM weatherincity WHERE cityId = :cityId")
    fun initiateGetCityByCityId(cityId: Long): WeatherInCity

    @Query("SELECT * FROM weatherincity WHERE name = :name")
    fun initiateGetCityByName(name: String): WeatherInCity

    @Update
    fun initiateUpdateCityWeather(weatherInCity: WeatherInCity)

    @Query("DELETE FROM weatherincity WHERE cityId = :cityId")
    fun initiateDeleteCityByCityId(cityId: Long)

    @Query("DELETE FROM weatherincity WHERE name = :name AND country = :country")
    fun initiateDeleteCityByNameAndCountry(name: String, country: String)
}