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
import com.example.smarttrade.nonactivityclasses.product_representation


class ProductAdapter(
    private val popularProducts: MutableList<product_representation>
) : BaseAdapter() {
    override fun getCount(): Int {
        return popularProducts.count()
    }

    override fun getItem(position: Int): Any {
        return popularProducts.get(position)
    }

    override fun getItemId(position: Int): Long {
        return popularProducts.get(position).PN
    }

    fun addProductToList(product: product_representation){
        popularProducts.add(product)
    }
    fun notifyAddedProduct(product: product_representation){
        notifyDataSetChanged()
    }

    fun addAllProducts(productList: MutableList<product_representation>){
        for(product in productList) {
            popularProducts.add(product)
        }
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.product_representation, parent, false)

        // Find UI elements in the inflated view
        val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewTitulo)
        val textViewStock = view.findViewById<TextView>(R.id.textViewCantStock)
        val imageViewLeaf = view.findViewById<ImageView>(R.id.imageViewLeaf)

        // Get the data object for this position

        // Set data to UI elements

        //Codigo para cuando la lista este en funcionamiento
        /*
        val bitmap = ImageURLtoBitmapConverter.downloadImage(popularProducts[position].image)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(R.drawable.no_photo)
        }
        textViewPrice.text = popularProducts[position].price
        textViewName.text = popularProducts[position].name
        textViewStock.text = popularProducts[position].stock.toString()
        when(popularProducts[position].leafColor){
            LeafColor.RED -> imageView.setImageResource(R.drawable.hoja_roja)
            LeafColor.YELLOW -> imageView.setImageResource(R.drawable.hoja_amarilla)
            LeafColor.GREEN -> imageView.setImageResource(R.drawable.hoja_verde)
        }
        */

        //Temporal para probar
        imageView.setImageResource(R.drawable.no_photo)
        textViewPrice.text = "10"
        textViewName.text = "Prueba"
        textViewStock.text = "2"
        imageViewLeaf.setImageResource(R.drawable.hoja_roja)

        return view
    }

}