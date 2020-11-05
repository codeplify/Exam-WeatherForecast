package com.homecredit.exam.model

//data class City(val city: String, val weather: String, val temperature: String ,val isFavorite: Boolean)


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "city_weather_table")
class City (@PrimaryKey
             @SerializedName("id")
             @ColumnInfo(name = "id") val id:Int,

            @SerializedName("city")
             @ColumnInfo(name = "city") val city:String,

            @SerializedName("weather")
             @ColumnInfo(name = "weather") val weather:String,

            @SerializedName("isFavorite")
            @ColumnInfo(name = "isFavorite") var isFavorite:Int,

            @SerializedName("temp")
            @ColumnInfo(name = "temp") val temp:String,

            val highLow:String = ""

): Serializable {
}