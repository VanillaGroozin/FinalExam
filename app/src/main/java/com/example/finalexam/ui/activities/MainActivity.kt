package com.example.finalexam.ui.activities

import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.finalexam.R
import com.example.finalexam.ui.fragment.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openWeatherFragment();
    }

    private fun openWeatherFragment() {
        val weatherFragment = WeatherFragment();
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.WeatherFragment, weatherFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}