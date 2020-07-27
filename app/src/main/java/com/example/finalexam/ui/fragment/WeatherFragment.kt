package com.example.finalexam.ui.fragment

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.finalexam.R
import com.example.finalexam.domain.usecase.GetWeatherUseCase
import com.example.finalexam.mvp.presenter.WeatherPresenter
import com.example.finalexam.ui.items.CountryItem
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {
    private lateinit var weatherPresenter: WeatherPresenter
    var CITY: String = "moscow"
    val API: String = "5059fd19886accfbb1c633ea2b224c8e"
    var buttonAction: LinearLayout? = null
    var getWeatherUseCase: GetWeatherUseCase = GetWeatherUseCase()
    var v: View? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_weather, container, false)
        initializeViews()
        initializeListeners()

        val bundle = arguments
        if (bundle != null) {
            val details = bundle.getParcelable<CountryItem>("COUNTRY_NAME")
            CITY = details?.countryName.toString();
        }
        weatherTask().execute()
        return v
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
////      weatherPresenter = WeatherPresenter(this, WeatherInteractor())
//
//        val textFragment = CityFragment()
//
//        // Get the support fragment manager instance
//        val manager = supportFragmentManager
//
//        // Begin the fragment transition using support fragment manager
//        val transaction = manager.beginTransaction()
//
//        // Replace the fragment on container
//        transaction.replace(R.id.CityFragment, textFragment)
//        transaction.addToBackStack(null)
//
//        // Finishing the transition
//        transaction.commit()
//        initializeViews()
//        initializeListeners()
//        //weatherTask().execute()
//        initiateRequestWeather()
//        //getCurrentData()
//    }

//    private fun getCurrentData() {
//        var service: WeatherService = ApiConnection().initializeAPI()
//        val call = service.getCurrentWeatherData(lat, lon, AppId)
//        call.enqueue(object : Callback<WeatherResponse> {
//            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                if (response.code() == 200) {
//                    val weatherResponse = response.body()!!
//                    val stringBuilder = "Country: " +
//                            weatherResponse.sys!!.country +
//                            "\n" +
//                            "Temperature: " +
//                            weatherResponse.main!!.temp +
//                            "\n" +
//                            "Temperature(Min): " +
//                            weatherResponse.main!!.temp_min +
//                            "\n" +
//                            "Temperature(Max): " +
//                            weatherResponse.main!!.temp_max +
//                            "\n" +
//                            "Humidity: " +
//                            weatherResponse.main!!.humidity +
//                            "\n" +
//                            "Pressure: " +
//                            weatherResponse.main!!.pressure
//                    findViewById<TextView>(R.id.address).text = stringBuilder.toString()
//                }
//            }
//
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//
//            }
//        })
//    }

//    companion object {
//
//        var BaseUrl = "http://api.openweathermap.org/"
//        var AppId = "5059fd19886accfbb1c633ea2b224c8e"
//        var lat = "43.238949"
//        var lon = "76.889709"
//    }
//
//    private fun initiateRequestWeather() {
//        val weatherResponse = getWeatherUseCase.getWeatherResponse()
//        if (weatherResponse != null){
//            val main = weatherResponse?.main
//            val sys = weatherResponse?.sys
//            val wind = weatherResponse?.wind
//            val weather = weatherResponse?.weather
//            val updatedAt: Float? = weatherResponse?.dt
//            val temp = weatherResponse?.main?.temp.toString() + "°C"
//            val tempMin = "Min Temp: " + weatherResponse?.main?.temp_min + "°C"
//            val tempMax = "Max Temp: " + weatherResponse?.main?.temp_max + "°C"
//            val pressure = weatherResponse?.main?.pressure
//            val humidity = weatherResponse?.main?.humidity
//
//            val sunrise: Long? = weatherResponse?.sys?.sunrise
//            val sunset: Long? = weatherResponse?.sys?.sunset
//            val windSpeed = weatherResponse?.wind?.speed
//            val weatherDescription = weatherResponse?.weather?.first()?.description
//
//            val address = weatherResponse?.name + ", " + weatherResponse?.sys?.country
//
//            findViewById<TextView>(R.id.address).text = address
//            findViewById<TextView>(R.id.updated_at).text = "updatedAtText"
//            findViewById<TextView>(R.id.status).text = weatherDescription
//            findViewById<TextView>(R.id.temp).text = temp
//            findViewById<TextView>(R.id.temp_min).text = tempMin
//            findViewById<TextView>(R.id.temp_max).text = tempMax
//            findViewById<TextView>(R.id.sunrise).text =
//                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise!! * 1000))
//            findViewById<TextView>(R.id.sunset).text =
//                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset!! * 1000))
//            findViewById<TextView>(R.id.wind).text = windSpeed.toString()
//            findViewById<TextView>(R.id.pressure).text = pressure.toString()
//            findViewById<TextView>(R.id.humidity).text = humidity.toString()
//        }
//    }

    private fun initializeViews(){
        buttonAction = v?.findViewById(R.id.addressContainer)
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

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            v?.findViewById<ProgressBar>(R.id.loader)?.visibility = View.VISIBLE
            v?.findViewById<ConstraintLayout>(R.id.mainContainer)?.visibility = View.GONE
            v?.findViewById<TextView>(R.id.errorText)?.visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                v?.findViewById<TextView>(R.id.address)?.text = address
                v?.findViewById<TextView>(R.id.updated_at)?.text =  updatedAtText
                v?.findViewById<TextView>(R.id.status)?.text = weatherDescription.capitalize()
                v?.findViewById<TextView>(R.id.temp)?.text = temp
                v?.findViewById<TextView>(R.id.temp_min)?.text = tempMin
                v?.findViewById<TextView>(R.id.temp_max)?.text = tempMax
                v?.findViewById<TextView>(R.id.sunrise)?.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date(sunrise*1000)
                )
                v?.findViewById<TextView>(R.id.sunset)?.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date(sunset*1000)
                )
                v?.findViewById<TextView>(R.id.wind)?.text = windSpeed
                v?.findViewById<TextView>(R.id.pressure)?.text = pressure
                v?.findViewById<TextView>(R.id.humidity)?.text = humidity
                v?.findViewById<ProgressBar>(R.id.loader)?.visibility = View.GONE
                v?.findViewById<ConstraintLayout>(R.id.mainContainer)?.visibility = View.VISIBLE

            } catch (e: Exception) {
                v?.findViewById<ProgressBar>(R.id.loader)?.visibility = View.GONE
                v?.findViewById<TextView>(R.id.errorText)?.visibility = View.VISIBLE
            }

        }
    }
}