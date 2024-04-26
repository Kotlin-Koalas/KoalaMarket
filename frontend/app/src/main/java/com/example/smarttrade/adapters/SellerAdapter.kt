package com.example.smarttrade.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.seller_representation

class SellerAdapter(

    private val sellerList : MutableList<seller_representation>

) : BaseAdapter()
 {
     override fun getCount(): Int {
         return sellerList.count()
     }

     override fun getItem(position: Int): Any {
         return sellerList.get(position)
     }

     override fun getItemId(position: Int): Long {
         return position.toLong()
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         val view = convertView ?: LayoutInflater.from(parent?.context)
             .inflate(R.layout.seller_representation, parent, false)

         val textNameSeller = view.findViewById<TextView>(R.id.sellerName)
         val textPrice = view.findViewById<TextView>(R.id.price)
         val stockText = view.findViewById<TextView>(R.id.stock)
         val cart = view.findViewById<ImageView>(R.id.carritoImageView)

         textNameSeller.text = sellerList[position].name
         textPrice.text = sellerList[position].price.toString()
         stockText.text = sellerList[position].stock.toString()

         cart.setOnClickListener {
             // TODO: implementar que se añada al carrito a ese precio

         }

         return view
     }


 }