package com.example.smarttrade

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.nonactivityclasses.ImageURLtoBitmapConverter
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
        return position.toLong()
    }

    fun addProductToList(product: product_representation) {
        popularProducts.add(product)
    }

    fun notifyAddedProduct(product: product_representation) {
        notifyDataSetChanged()
    }

    fun addAllProducts(productList: MutableList<product_representation>) {
        for (product in productList) {
            popularProducts.add(product)
        }
        notifyDataSetChanged()
    }

    fun updateProducts(updateProductList: MutableList<product_representation>) {
        popularProducts.clear()
        popularProducts.addAll(updateProductList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.product_representation, parent, false)

        // Find UI elements in the inflated view
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewTitulo)
        val textViewStock = view.findViewById<TextView>(R.id.textViewCantStock)
        val imageViewLeaf = view.findViewById<ImageView>(R.id.imageViewLeaf)

        ImageURLtoBitmapConverter.downloadImage(popularProducts[position].image,view)

        textViewPrice.text = popularProducts[position].price
        textViewName.text = popularProducts[position].name
        textViewStock.text = popularProducts[position].stock.toString()
        when(popularProducts[position].leafColor){
            "red" -> imageViewLeaf.setImageResource(R.drawable.hoja_roja)
            "yellow" -> imageViewLeaf.setImageResource(R.drawable.hoja_amarilla)
            "green" -> imageViewLeaf.setImageResource(R.drawable.hoja_verde)
        }

        /*
        //Temporal para probar
        imageView.setImageResource(R.drawable.no_photo)
        textViewPrice.text = "10"
        textViewName.text = "Prueba"
        textViewStock.text = "2"
        imageViewLeaf.setImageResource(R.drawable.hoja_roja)
        */

        val productRepresentation = view.findViewById<ConstraintLayout>(R.id.layout)
        productRepresentation.setOnClickListener {
            //TODO ir a la pagina del propio producto
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