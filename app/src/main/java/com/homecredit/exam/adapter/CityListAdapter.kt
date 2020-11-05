package com.homecredit.exam.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.homecredit.exam.R
import com.homecredit.exam.model.City
import androidx.recyclerview.widget.RecyclerView
import com.homecredit.exam.model.CityListener
import kotlinx.android.synthetic.main.city_list.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CityListAdapter(private val cities:List<City>) : RecyclerView.Adapter<CityListAdapter.ViewHolder>(){

    var cityListener: CityListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.city_name
        val cityWeather: TextView = view.city_weather
        val cityTemp: TextView = view.city_temp
        val cityHolder : LinearLayout = view.city_holder
        val imageHolder: ImageView = view.image_is_favorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {
        val item = cities[position]

        val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING

        p0.cityName.text = item.city
        p0.cityWeather.text = item.weather
        p0.cityTemp.text = "${df.format(item.temp.toDouble())} \u2103"
        p0.cityHolder.setOnClickListener {cityListener!!.onOpen(item)}

        when(item.temp.toDouble()){
            in -1..0 -> p0.cityHolder.setBackgroundColor(Color.parseColor("#1976D2"))
            in 0..15 -> p0.cityHolder.setBackgroundColor(Color.parseColor("#26C6DA"))
            in 15..30 -> p0.cityHolder.setBackgroundColor(Color.parseColor("#66BB6A"))
            in 30..100 -> p0.cityHolder.setBackgroundColor(Color.parseColor("#FF7043"))
        }

        if(item.isFavorite == 1){
            p0.imageHolder.isVisible = true
        }
    }

}