package com.example.finalexam.data.repository


import WeatherResponse
import android.text.Html
import android.widget.TextView
import androidx.annotation.NonNull
import com.example.finalexam.R
import com.example.finalexam.data.api.ApiImplementation
import com.example.finalexam.data.api.WeatherService
import com.example.finalexam.domain.repository.WeatherDomainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherRepository : WeatherDomainRepository {

    var apiImplementation: ApiImplementation = ApiImplementation()
    var AppId = "5059fd19886accfbb1c633ea2b224c8e"
    var lat = "35"
    var lon = "139"

    constructor()

    override fun initiateGetWeather(): WeatherResponse? {
        val call = apiImplementation.getCurrentWeatherData(lat, lon, AppId)
        var weatherResponse: WeatherResponse? = null

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherResponse = null
            }
        })
        return weatherResponse
    }
}



