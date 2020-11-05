package com.homecredit.exam.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.homecredit.exam.MainActivity
import com.homecredit.exam.R
import com.homecredit.exam.adapter.CityListAdapter
import com.homecredit.exam.model.City
import com.homecredit.exam.model.CityListener
import com.homecredit.exam.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment(), CityListener{
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var cities:ArrayList<City>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        if((activity as MainActivity).supportActionBar != null) {
            val actionBar = (activity as MainActivity).supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(false)
            actionBar.setHomeButtonEnabled(false)
        }

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.cityWeather.observe(viewLifecycleOwner, Observer {
                cities.clear()
                cities.addAll(it)
                recyler_city_list.adapter!!.notifyDataSetChanged()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
           if(it.isNotEmpty()){
               Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
           }
        })

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cities = ArrayList()
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyler_city_list.layoutManager = linearLayoutManager
        var tradeAdapter = CityListAdapter(cities)
        tradeAdapter.cityListener = this
        recyler_city_list.adapter = tradeAdapter

        var swipeContainer = activity!!.swipeContainer as SwipeRefreshLayout
        swipeContainer.setOnRefreshListener {
            GlobalScope.launch {
                viewModel.getAllCityWeather()
            }
            swipeContainer.isRefreshing = false
        }
    }

    override fun onOpen(city: City) {
        val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
        var bundle = Bundle()
            bundle.putString("query","${city.city},${city.id}")
        var detailsFragment = DetailsFragment()
            detailsFragment.arguments = bundle
        ft.replace(R.id.container, detailsFragment, "NewFragmentTag")
        ft.commit()
    }

}