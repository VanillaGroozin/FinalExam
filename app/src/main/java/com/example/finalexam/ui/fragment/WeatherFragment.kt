package com.example.finalexam.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.finalexam.R
import com.example.finalexam.data.WeatherAppDatabase
import com.example.finalexam.data.WeatherResponse
import com.example.finalexam.domain.usecase.WeatherUseCase
import com.example.finalexam.ui.items.CountryItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.step.weatherapp.data.City
import kz.step.weatherapp.data.WeatherInCity
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {
    var buttonAction: LinearLayout? = null
    var weatherResponse: WeatherResponse? = null
    var v: View? = null
    var CITY: String = "Moscow"
    var db: WeatherAppDatabase? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_weather, container, false)
        initializeViews()
        initializeListeners()

        db = Room.databaseBuilder(
            requireContext(),
            WeatherAppDatabase::class.java,
            "WeatherAppDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


        val bundle = arguments
        if (bundle != null) {
            val details = bundle.getParcelable<CountryItem>("COUNTRY_NAME")
            CITY = details?.countryName.toString();
        }
        initializeGetDataFromApi(CITY)
        return v
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeGetDataFromApi(city: String) {
        val observable = WeatherUseCase().initiateCreateObservableByQuery(city)
        var observer = observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFailure(t: Throwable) {
        if(weatherResponse == null) {
            if (cityWeatherInDb()) {
                Toast.makeText(context, "Loaded latest city info", Toast.LENGTH_SHORT).show()
            } else {
                initiateWeatherFragmentTransition()
                Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onResponse(response: WeatherResponse) {
        initiateViewsFromCityWeather(response)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun initiateViewsFromCityWeather(cityWeather: WeatherResponse) {
        var cityInWeather = WeatherUseCase().weatherResponseToCityInWeatherEntity(cityWeather)
        dataBind(cityInWeather)
        initializeNewCityWeatherInsert(cityWeather)
    }

    private fun dataBind(weatherIn: WeatherInCity) {
        val updatedAtText = "Updated at: " +
                SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(weatherIn.updateDt!!.toLong()*1000)
                )

        v?.findViewById<TextView>(R.id.address)?.text =
            weatherIn.name + ", " + weatherIn.country
        v?.findViewById<TextView>(R.id.updated_at)?.text = updatedAtText
        v?.findViewById<TextView>(R.id.status)?.text =
            weatherIn.weatherDescription
        v?.findViewById<TextView>(R.id.temp)?.text =
            weatherIn.mainTemp.toString() + "°C"
        v?.findViewById<TextView>(R.id.temp_min)?.text =
            "Min Temp: " + weatherIn.minTemp.toString() + "°C"
        v?.findViewById<TextView>(R.id.temp_max)?.text =
            "Max Temp: " + weatherIn.maxTemp.toString() + "°C"
        v?.findViewById<TextView>(R.id.sunrise)?.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                Date(weatherIn.sysSunrise!!*1000)
            )
        v?.findViewById<TextView>(R.id.sunset)?.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                Date(weatherIn.sysSunset!!*1000)
            )
        v?.findViewById<TextView>(R.id.wind)?.text = weatherIn.windSpeed.toString()
        v?.findViewById<TextView>(R.id.pressure)?.text =
            weatherIn.mainPressure.toString()
        v?.findViewById<TextView>(R.id.humidity)?.text =
            weatherIn.mainHumidity.toString()
        v?.findViewById<ProgressBar>(R.id.loader)?.visibility = View.GONE
        v?.findViewById<ConstraintLayout>(R.id.mainContainer)?.visibility = View.VISIBLE
    }

    private fun initializeNewCityWeatherInsert(weatherResponse: WeatherResponse) {
        var cityInDb = initializeGetCity(weatherResponse.name.toString())
        if (cityInDb == null) db?.getCityDao()?.initiateInsertCity(WeatherUseCase().weatherResponseToCityEntity(weatherResponse))

        var weatherInCityInDb = initializeGetCityWeather(weatherResponse.name.toString())
        if (weatherInCityInDb == null)
            db?.getCityInWeatherListDao()?.initiateInsertCity(WeatherUseCase().weatherResponseToCityInWeatherEntity(weatherResponse))
        else {
            db?.getCityInWeatherListDao()?.initiateUpdateCityWeather(WeatherUseCase().weatherResponseToCityInWeatherEntity(weatherResponse))
        }
    }

    private fun initializeGetCity(cityName: String): City? {
        return db?.getCityDao()?.initiateGetCityByName(cityName)
    }

    private fun initializeGetCityWeather(cityName: String): WeatherInCity? {
        return db?.getCityInWeatherListDao()?.initiateGetCityByName(cityName)
    }

    private fun initializeViews(){
        buttonAction = v?.findViewById(R.id.addressContainer)
    }

    private fun cityWeatherInDb(): Boolean {
        var weatherInCity = db?.getCityInWeatherListDao()?.initiateGetCityByName(CITY)
        if (weatherInCity != null) {
            dataBind(weatherInCity)
            return true
        } else return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initializeListeners(){
        buttonAction!!.setOnClickListener(View.OnClickListener {
            initiateWeatherFragmentTransition()
        })
    }

    private fun initiateWeatherFragmentTransition(){
        val cityFragment: Fragment = CityFragment()
        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.WeatherFragment, cityFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}