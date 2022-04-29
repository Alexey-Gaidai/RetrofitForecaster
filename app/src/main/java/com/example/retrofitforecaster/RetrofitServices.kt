package com.example.retrofitforecaster

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface RetrofitServices {
    @GET("forecast?&appid=625b6fd5c5be3e9c809460446a1fd3e9&units=metric&lang=ru")
    fun getWeatherList(@Query("q") name: String): Call<DataWeather>

    companion object {
        fun createApiService(): RetrofitServices {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitServices::class.java)
        }
    }
}