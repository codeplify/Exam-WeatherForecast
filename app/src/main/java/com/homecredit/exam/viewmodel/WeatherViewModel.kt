package com.homecredit.exam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.homecredit.exam.db.CityRoomDB
import com.homecredit.exam.model.City
import com.homecredit.exam.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WeatherRepository
    var cityWeather:LiveData<List<City>>
    var cityDetail: LiveData<City>
    var errorResponse: LiveData<String>

    init {
        val cityDao = CityRoomDB.getDatabase(application, viewModelScope).cityDao()
        repository = WeatherRepository(cityDao)
        cityWeather = repository.allWeather
        cityDetail = repository.cityWeather
        errorResponse = repository.errorResponse
    }

    fun save(city: City) = viewModelScope.launch{
        repository.insert(city)
    }

    fun update(city: City) = viewModelScope.launch{
        repository.updateWeather(city)
    }

    suspend fun getAllCityWeather(){
        repository.getCityWeather()
    }

    suspend fun getWeatherDetails(details: String){
        repository.getWeatherDetail(details)
    }
}

class WeatherViewModelDetails(application: Application) : AndroidViewModel(application){

}