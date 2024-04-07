package com.example.smarttrade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.nonactivityclasses.ImageURLtoBitmapConverter
import com.example.smarttrade.nonactivityclasses.LeafColor
import com.example.smarttrade.nonactivityclasses.category_representation
import com.example.smarttrade.nonactivityclasses.product_representation


class CategoryAdapter(
    private val categories: MutableList<category_representation>
) : BaseAdapter() {
    override fun getCount(): Int {
        return categories.count()
    }

    override fun getItem(position: Int): Any {
        return categories.get(position)
    }

    override fun getItemId(position: Int): Long {
        return categories.get(position).image.toLong()
    }

    fun addCategoryToList(category: category_representation){
        categories.add(category)
    }
    fun notifyAddedProduct(product: product_representation){
        notifyDataSetChanged()
    }

    fun addAllCategories(catList: MutableList<category_representation>){
        for(category in catList) {
            categories.add(category)
        }
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.category_select, parent, false)

        // Find UI elements in the inflated view
        val imageView = view.findViewById<ImageView>(R.id.imageViewCat)

        // Get the data object for this position

        val cat: Int = categories[position].image

        // Set data to UI elements

        imageView.setImageResource(cat)


        return view
    }

}