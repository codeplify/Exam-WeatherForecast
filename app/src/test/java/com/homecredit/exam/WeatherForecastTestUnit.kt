package com.homecredit.exam

import android.graphics.Color
import org.junit.Test

import org.junit.Assert.*

class WeatherForecastTestUnit {
    @Test
    fun isMatchWeather() {
        assertEquals("#1976D2", testSelectTemperature((-1).toDouble()))
        assertEquals("#66BB6A", testSelectTemperature(16.toDouble()))
    }

    fun testSelectTemperature(input: Double): String{

        when(input){
            in -32..0 -> return "#1976D2"
            in 0..15 -> return "#26C6DA"
            in 15..30 -> return "#66BB6A"
            in 30..100 ->return "#FF7043"
            else-> return ""
        }

        return ""
    }

}