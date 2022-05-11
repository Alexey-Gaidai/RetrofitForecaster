package com.example.retrofitforecaster.data

import com.example.retrofitforecaster.WeatherDataNW
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

interface WeatherService {
    companion object {
        fun createApiService(): WeatherService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherService::class.java)
        }
    }

    @GET("/data/2.5/forecast?")
    fun getWeatherList(
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("q") city: String,
    ): Call<WeatherDataNW>
}