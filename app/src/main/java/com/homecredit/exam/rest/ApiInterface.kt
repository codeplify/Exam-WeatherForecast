package com.homecredit.exam.rest

import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type:application/json","Cache-Control:no-cache")
    @GET("data/2.5/group")
    fun getListCities(@Query("id") id: String?, @Query("appid") appid: String?, @Query("units") units: String):retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json","Cache-Control:no-cache")
    @GET("data/2.5/weather")
    fun getWeather(@Query("q") id: String?, @Query("appid") appid: String?, @Query("units") units: String):retrofit2.Call<ResponseBody>


}