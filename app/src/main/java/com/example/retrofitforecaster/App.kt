package com.example.retrofitforecaster

import android.app.Application
import com.example.retrofitforecaster.data.WeatherService

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        apiService = WeatherService.createApiService()
    }

    companion object {
        lateinit var apiService: WeatherService
            private set
    }
}