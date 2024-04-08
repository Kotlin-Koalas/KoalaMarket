package com.example.smarttrade

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.nonactivityclasses.ImageURLtoBitmapConverter
import com.example.smarttrade.nonactivityclasses.LeafColor
import com.example.smarttrade.nonactivityclasses.category_representation
import com.example.smarttrade.nonactivityclasses.product_representation
import com.example.smarttrade.nonactivityclasses.search_representation


class SearchAdapter(
    private val prevSearch: MutableList<search_representation>
) : RecyclerView.Adapter<SearchAdapter.CategoryViewHolder>() {

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
            BrowseProducts.updateSearch(curS.search)
        }
    }

}