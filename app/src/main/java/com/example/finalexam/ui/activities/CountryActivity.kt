package com.example.finalexam.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalexam.R
import com.example.finalexam.mvp.view.WeatherView
import com.example.finalexam.ui.adapters.CountryAdapter
import com.example.finalexam.ui.items.CountryItem
import kotlinx.android.synthetic.main.country_activity.*

class CountryActivity :AppCompatActivity(),
    WeatherView {
    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.country_activity)
//
//        val     exampleList = GenerateDummyList(500)
//        recycler_view.adapter = CountryAdapter(exampleList, this)
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.setHasFixedSize(true)

        //initializeDefaultFragment()
    }
    private  fun GenerateDummyList(size: Int): List<CountryItem>{
        val list = ArrayList<CountryItem>()
        for (i in 0 until size){
            val item = CountryItem("Country #$i")
            list += item
        }
        return list
    }


    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun getDataSuccess() {
        TODO("Not yet implemented")
    }

    override fun getDataFailed() {
        TODO("Not yet implemented")
    }
}