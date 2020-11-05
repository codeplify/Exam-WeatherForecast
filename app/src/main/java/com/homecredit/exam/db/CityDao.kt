package com.homecredit.exam.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.homecredit.exam.model.City

@Dao
interface CityDao {

    @androidx.room.Query("SELECT * FROM city_weather_table")
    fun getAllCity(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("SELECT * FROM city_weather_table WHERE id = :id")
    fun getCity(id:Int): City

    @Update
    suspend fun updateCity(vararg weather: City)

    @androidx.room.Query("DELETE FROM city_weather_table")
    suspend fun deleteAll()

}