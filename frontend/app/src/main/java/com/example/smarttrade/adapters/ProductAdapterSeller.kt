package com.example.smarttrade.adapters

import android.graphics.Bitmap
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.R
import com.example.smarttrade.SellerFragment
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter


class ProductAdapterSeller(
    private val popularProducts: MutableList<product_representation>,
) :BaseAdapter(){

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
            .inflate(R.layout.seller_product_view, parent, false)

         val sellerFragment = SellerFragment()
         var currentPrice = ""

        // Find UI elements in the inflated view
        val updatePrice = view.findViewById<ImageView>(R.id.imageViewCheckPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewNameProduct)
        val textPrice = view.findViewById<EditText>(R.id.editTextPriceProduct)
        val imageProduct = view.findViewById<ImageView>(R.id.imageView9)
        val deleteProduct = view.findViewById<ImageView>(R.id.imageViewDeleteProduct)
        val pNProduct = popularProducts[position].PN

        ImageURLtoBitmapConverter.downloadImage(popularProducts[position].image, view)
        textViewName.text = popularProducts[position].name
        textPrice.hint = popularProducts[position].price
        //TODO añadir posible stock

        textPrice.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nada
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentPrice = s.toString()
            }
            override fun afterTextChanged(s: android.text.Editable?) {
                //nada
            }

        })

        val patternPrice = "^[0-9]*(\\.[0-9]{2})$".toRegex()
        val productRepresentation = view.findViewById<ConstraintLayout>(R.id.layoutProductSeller)


        //TODO cambiar parámetros a enviar
        deleteProduct.setOnClickListener {
            sellerFragment.showAlertDeleteProductBox("¿Estás seguro de que quieres borrar este producto?",
                                                    "","")

        }

        updatePrice.setOnClickListener(){

            if(currentPrice != "" && currentPrice != popularProducts[position].price && patternPrice.containsMatchIn(currentPrice)){
                sellerFragment.showAlertChangePriceProductBox("¿Estás seguro de que quieres cambiar el precio de este producto?"
                    ,"","","")
            }

        }

        return view

    }




    companion object{
        fun setImage(image: Bitmap?, view: View){
            val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
            if (image != null) {
                imageView.setImageBitmap(image)
            }
        }
    }

}



