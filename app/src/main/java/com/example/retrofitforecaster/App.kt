package com.example.retrofitforecaster

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        apiService = RetrofitServices.createApiService()
    }

    companion object {
        lateinit var apiService: RetrofitServices
            private set
    }
}