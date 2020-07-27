package com.example.finalexam.mvp.view

interface WeatherView {
    fun showProgress()
    fun hideProgress()
    fun getDataSuccess()
    fun getDataFailed()
}