package com.homecredit.exam.repository

import com.homecredit.exam.model.City
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {
    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Test
    fun getAllWeather() {
        Mockito.`when`(weatherRepository.getWeatherDetail("Manila,1701668")).thenReturn(false)
    }

    @Test
    fun getCityWeather() {
        Mockito.doReturn(weatherRepository.getWeatherDetail("Manila,1701668")).`when`(weatherRepository).getWeatherDetail("")
    }

    @Test
    fun testCity(){
        val mockService = Mockito.mock(City::class.java)
        Mockito.`when`(mockService.temp.isEmpty()).thenReturn(true)
    }
}