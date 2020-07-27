package com.example.finalexam.mvp.model

class WeatherInteractor {
    interface OnFinishedListener{
        fun onResultSuccess()
        fun onResultFail()
    }
}