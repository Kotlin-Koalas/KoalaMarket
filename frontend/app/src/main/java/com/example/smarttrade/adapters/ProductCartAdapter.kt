package com.example.smarttrade.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.R
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter
import com.example.smarttrade.models.product_representation


class ProductCartAdapter(
    private val cartProducts: MutableList<product_representation>
) : BaseAdapter() {

    val who = this

    override fun getCount(): Int {
        return cartProducts.count()
    }

    override fun getItem(position: Int): Any {
        return cartProducts.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addProductToList(product: product_representation) {
        cartProducts.add(product)
    }

    fun notifyAddedProduct(product: product_representation) {
        notifyDataSetChanged()
    }

    fun addAllProducts(productList: MutableList<product_representation>) {
        for (product in productList) {
            cartProducts.add(product)
        }
        notifyDataSetChanged()
    }

    fun updateProducts(updateProductList: MutableList<product_representation>) {
        cartProducts.clear()
        cartProducts.addAll(updateProductList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.product_representation_cart, parent, false)

        // Find UI elements in the inflated view
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewTitulo)

        ImageURLtoBitmapConverter.downloadImage(cartProducts[position].image,view)

        textViewPrice.text = cartProducts[position].price
        textViewName.text = cartProducts[position].name

        val selected = view.findViewById<ImageView>(R.id.imageViewSelected)
        selected.setOnClickListener {
            //TODO manage que se ha seleccionado un producto
        }

        return view
    }

    companion object{
        fun setImage(image: Bitmap?,view:View){
            val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
            if (image != null) {
                imageView.setImageBitmap(image)
            }
        }
    }

}