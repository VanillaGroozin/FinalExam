package com.example.finalexam.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.init(countryList.get(position), clickListener)
        //holder.itemView.country_name = currentItem.countryName
    }


    override fun getItemCount() = countryList.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.country_name

        fun init (item: CountryItem, action: OnCountryItemClickListener){
            textView.text = item.countryName

            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnCountryItemClickListener {
        fun onItemClick(item: CountryItem, position: Int)
    }

}