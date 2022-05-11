package com.example.retrofitforecaster.data

data class WeatherDataUI(
    val temperature: Double,
    val dateTime: String,
    val pressure: Int,
    val iconLink: String
)