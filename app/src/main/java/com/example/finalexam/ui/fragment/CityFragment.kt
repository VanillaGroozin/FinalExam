package com.example.finalexam.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.finalexam.R
import com.example.finalexam.data.WeatherAppDatabase
import com.example.finalexam.ui.adapters.CountryAdapter
import com.example.finalexam.ui.items.CountryItem

class CityFragment : Fragment(), CountryAdapter.OnCountryItemClickListener {
    var v: View? = null
    private var db: WeatherAppDatabase? = null
    private var find_button: Button? = null
    private var city_text: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.country_activity, container, false)


        db = Room.databaseBuilder(
            requireContext(),
            WeatherAppDatabase::class.java,
            "WeatherAppDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val exampleList = FillCityList()
        var videoRecyclerview = v?.findViewById(R.id.recycler_view) as RecyclerView
        videoRecyclerview.layoutManager = LinearLayoutManager(activity)
        videoRecyclerview.adapter = CountryAdapter(exampleList, this)

        initializeViews()
        initializeListeners()
        return v
    }

    private fun initializeListeners(){
        find_button!!.setOnClickListener(View.OnClickListener {
            showSelectedCityWeather(city_text?.text.toString())
        })
    }

    private fun initializeViews(){
        find_button = v?.findViewById(R.id.find_city_button)
        city_text = v?.findViewById(R.id.find_city_edit_text)
    }


    private fun FillCityList(): List<CountryItem>{
        val list = ArrayList<CountryItem>()
        var cities = db?.getCityDao()?.initiateGetCities()
        if (cities != null) {
            for (city in cities) {
                list += CountryItem(city.name)
            }
        }
        return list
    }

    override fun onItemClick(item: CountryItem, position: Int) {
        var countryName = item.countryName;
        showSelectedCityWeather(countryName)
    }

    override fun onDeleteClick(item: CountryItem, position: Int) {
        db?.getCityDao()?.initiateDeleteCityByName(item.countryName)
    }

    private fun showSelectedCityWeather(countryName: String){
        val weatherFragment: Fragment = WeatherFragment()
        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable("COUNTRY_NAME", CountryItem(countryName))
        weatherFragment.arguments = bundle
        fragmentTransaction?.replace(R.id.WeatherFragment, weatherFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}