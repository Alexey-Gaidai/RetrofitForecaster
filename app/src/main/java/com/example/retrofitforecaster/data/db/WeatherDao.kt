package com.example.retrofitforecaster.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitforecaster.data.WeatherDataUI

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<WeatherEntity>)

    @Query("SELECT * FROM weather order by date")
    suspend fun getAll(): List<WeatherEntity>

    @Query("delete from weather")
    suspend fun nuke()

    @Query("select count(*) from weather")
    suspend fun count(): Int
}