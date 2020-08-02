package com.example.finalexam.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexam.R
import com.example.finalexam.ui.items.CountryItem
import kotlinx.android.synthetic.main.country_item.view.*

class CountryAdapter(private val countryList: List<CountryItem>, private val clickListener: OnCountryItemClickListener) :RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)

        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentItem = countryList[position]
        holder.textView.text = currentItem.countryName
        holder.init(countryList[position], clickListener)
    }

    override fun getItemCount() = countryList.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.country_name
        private val deleteBtn: Button = itemView.imageview_viewholder_city_weather_delete_city
        fun init (item: CountryItem, action: OnCountryItemClickListener){

            textView.text = item.countryName
            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            deleteBtn.imageview_viewholder_city_weather_delete_city.setOnClickListener{
                action.onDeleteClick(item, adapterPosition)
                }
            }
        }
    }

    interface OnCountryItemClickListener {
        fun onItemClick(item: CountryItem, position: Int)
        fun onDeleteClick(item: CountryItem, position: Int)
    }

}