package com.example.retrofitforecaster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {
    private val mService = App.apiService
    val weatherData: MutableLiveData<List<ListItem>> = MutableLiveData()
    val weatherList: LiveData<List<ListItem>> get() = weatherData

    init {
        getWeather("Moscow")
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            mService.getWeatherList(city).enqueue(object : Callback<DataWeather> {
                override fun onResponse(call: Call<DataWeather>, response: Response<DataWeather>) {
                    val responseData = response.body() as DataWeather
                    weatherData.value = responseData.list
                }

                override fun onFailure(call: Call<DataWeather>, t: Throwable) {
                    weatherData.value = emptyList()
                }
            })
        }
    }

    fun loadFromDb() {

    }
}