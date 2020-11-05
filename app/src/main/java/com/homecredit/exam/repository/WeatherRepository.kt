package com.homecredit.exam.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homecredit.exam.db.CityDao
import com.homecredit.exam.model.City
import com.homecredit.exam.rest.ApiInterface
import com.homecredit.exam.rest.RetrofitInstance
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

class WeatherRepository (private val cityDao: CityDao){
    val retWeather = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    var allWeather: LiveData<List<City>> = cityDao.getAllCity()
    var cityWeather: MutableLiveData<City> = MutableLiveData()
    var errorResponse: MutableLiveData<String> = MutableLiveData()

    suspend fun insert(city: City){
        cityDao.insert(city)
    }

    suspend fun updateWeather(city: City){
        cityDao.updateCity(city)
    }

    fun getWeatherDetail(details: String):Boolean{
        var jsonRes = ""
        var response = retWeather.getWeather(details,"214f4db4e911babafcfee8b771b03127","metric").execute()
        if(response.isSuccessful){
            jsonRes = response.body()!!.string()
            val rootObject = JSONObject(jsonRes)
            var weather = rootObject.getJSONArray("weather").getJSONObject(0).getString("main")
            var tempMin = rootObject.getJSONObject("main").getString("temp_min")
            var tempMax = rootObject.getJSONObject("main").getString("temp_max")
            var temp = rootObject.getJSONObject("main").getString("temp")
            var city = rootObject.getString("name")
            var id = rootObject.getInt("id")
            val df = DecimalFormat("#")
                df.roundingMode = RoundingMode.CEILING
            cityWeather.postValue(City(id,city,weather,cityDao.getCity(id).isFavorite,temp, highLow = "High ${df.format(tempMax.toDouble())} \u2103/ Low ${df.format(tempMin.toDouble())} \u2103"))
            return false
        }

        return true
    }


    suspend fun getCityWeather() : ArrayList<City>{

        var cities = ArrayList<City>()
        cityDao.deleteAll()

        var jsonRes = ""
        var response = retWeather.getListCities("1701668,3067696,1835848","214f4db4e911babafcfee8b771b03127","metric").execute()
        if(response.isSuccessful){

            jsonRes = response.body()!!.string()
            val rootObject = JSONObject(jsonRes)
            val cnt = rootObject.getInt("cnt")
            val list = rootObject.getJSONArray("list")
            for (i in 0 until list.length()) {
                var name = list.getJSONObject(i).getString("name")
                var id = list.getJSONObject(i).getString("id")
                var temp = list.getJSONObject(i).getJSONObject("main").getDouble("temp")
                var weather = list.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main")
                cityDao.insert(City(Integer.parseInt(id),name, weather, 0, temp.toString()))
            }

            allWeather = MutableLiveData(cities)
        }

        if(response.code() != 200){
            allWeather = MutableLiveData()
            errorResponse.postValue("Response Code: ${response.code()} / Error Message: ${response.message()}")
        }
        return ArrayList()
    }
}