package com.example.smarttrade.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BrowseProductsFiltered
import com.example.smarttrade.R

//import com.example.smarttrade.models.LeafColor

import com.example.smarttrade.models.search_representation


class SearchAdapterFiltered(
    private val prevSearch: MutableList<search_representation>
) : RecyclerView.Adapter<SearchAdapterFiltered.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recommended_search_representation,
                parent,
                false
            )
        )
    }

    fun addAllCategories(list:MutableList<search_representation>){
        for(s in list){
            prevSearch.add(s)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return prevSearch.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val curS = prevSearch[position]
        val textSearch = holder.itemView.findViewById<TextView>(R.id.textViewPreviousSearch)
        textSearch.text = curS.search
        val selected = holder.itemView.findViewById<ConstraintLayout>(R.id.layoutSearch)

        selected.setOnClickListener {
            BrowseProductsFiltered.updateSearch(curS.search)
        }
    }

}