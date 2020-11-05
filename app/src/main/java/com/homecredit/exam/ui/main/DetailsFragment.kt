package com.homecredit.exam.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.homecredit.exam.MainActivity
import com.homecredit.exam.R
import com.homecredit.exam.model.City
import com.homecredit.exam.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat


class DetailsFragment: Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var city: City

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        var query = arguments!!.getString("query")
        val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING


        if ((activity as MainActivity).supportActionBar != null) {
            val actionBar = (activity as MainActivity).supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        GlobalScope.launch {
            viewModel.getWeatherDetails(query!!)
        }
        viewModel.cityDetail.observe(viewLifecycleOwner, Observer {
           tv_city_name.text = it.city
           city_weather.text =  it.weather
           tv_main_temp.text = "${df.format(it.temp.toDouble())} \u2103"
           city_temperature.text = it.highLow
           if(it.isFavorite.toString().contains("1")){
               btn_toggle_favorite.textOn
           }else{
               btn_toggle_favorite.textOff
           }

            btn_toggle_favorite.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    it.isFavorite = 1
                    viewModel.update(it)
                }else{
                    it.isFavorite = 0
                    viewModel.update(it)
                }
            }
        })
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

}