package com.example.retrofitforecaster.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitforecaster.App
import com.example.retrofitforecaster.WeatherDataNW
import com.example.retrofitforecaster.ListItem
import com.example.retrofitforecaster.data.WeatherDataUI
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val APP_ID = "625b6fd5c5be3e9c809460446a1fd3e9"
private const val UNITS = "metric"
private const val LANGUAGE = "ru"


class WeatherViewModel() : ViewModel() {
    private val mService = App.apiService
    val weatherData: MutableLiveData<List<WeatherDataUI>> = MutableLiveData()
    val weatherList: LiveData<List<WeatherDataUI>> get() = weatherData

    init {
        getWeather("Moscow")
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            mService.getWeatherList(APP_ID, UNITS, LANGUAGE, city).enqueue(object : Callback<WeatherDataNW> {
                override fun onResponse(call: Call<WeatherDataNW>, response: Response<WeatherDataNW>) {
                    weatherData.value = convertToModelUI(response.body()?.list)
                }

                override fun onFailure(call: Call<WeatherDataNW>, t: Throwable) {
                    weatherData.value = emptyList()
                }
            })
        }
    }

    private fun convertToModelUI(list: List<ListItem>?): List<WeatherDataUI>? {
        val weatherUI = list?.map { pointWeather ->
            WeatherDataUI(
                temperature = pointWeather.main.temp,
                pressure = pointWeather.main.pressure,
                dateTime = pointWeather.dt_txt,
                iconLink = "https://openweathermap.org/img/w/" + pointWeather.weather?.get(0)?.icon + ".png"
            )
        }
        return weatherUI
    }

}