package com.example.retrofitforecaster.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val temperature: Double,
    val dateTime: String,
    val pressure: Int,
    val iconLink: String
)