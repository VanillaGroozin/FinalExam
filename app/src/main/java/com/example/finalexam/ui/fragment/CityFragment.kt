package com.example.finalexam.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexam.R
import com.example.finalexam.ui.adapters.CountryAdapter
import com.example.finalexam.ui.items.CountryItem
import kotlinx.android.synthetic.main.country_item.*

class CityFragment : Fragment(), CountryAdapter.OnCountryItemClickListener {
    var v: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_city, container, false)

        val exampleList = GenerateDummyList(500)
        var videoRecyclerview = v?.findViewById(R.id.recycler_view) as RecyclerView
        videoRecyclerview.layoutManager = LinearLayoutManager(activity)
        videoRecyclerview.adapter = CountryAdapter(exampleList, this)
        return v
    }

    private fun GenerateDummyList(size: Int): List<CountryItem>{
        val list = ArrayList<CountryItem>()
        list += CountryItem("Almaty")
        list += CountryItem("Astana")
        list += CountryItem("Moscow")
        return list
    }

    override fun onItemClick(item: CountryItem, position: Int) {
        var countryName = item.countryName;

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