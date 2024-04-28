package com.example.smarttrade.adapters

import android.util.Log
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

         who = this

         val textNameSeller = view.findViewById<TextView>(R.id.sellerName)
         val textPrice = view.findViewById<TextView>(R.id.price)
         val stockText = view.findViewById<TextView>(R.id.stockProduct)
         val cart = view.findViewById<ImageView>(R.id.carritoImageView)
         val specificAttirbutes = view.findViewById<TextView>(R.id.specificAttribute)

         textNameSeller.text = sellerList[position].vendorName
         textPrice.text = sellerList[position].price
         stockText.text = sellerList[position].stock



         cart.setOnClickListener {
             // TODO: implementar que se añada al carrito a ese precio

         }

         return view
     }

     companion object{
         private lateinit var who: SellerAdapter
         fun setSellerList(sellerList: MutableList<seller_representation>){
             who.sellerList.clear()
             who.sellerList.addAll(sellerList)
             who.notifyDataSetChanged()
         }
     }


 }