package com.example.smarttrade.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.material3.MediumTopAppBar
import com.example.smarttrade.R
import com.example.smarttrade.mediador.Mediador
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter
import com.example.smarttrade.models.product_representation_cart


class ProductCartAdapter(
) : BaseAdapter() {

    override fun getCount(): Int {
        return PersonBuyer.getShoppingCart().count()
    }

    override fun getItem(position: Int): Any {
        return PersonBuyer.getShoppingCart().get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun removeProduct(position: Int){
        PersonBuyer.removeProductFromCart(position)
        views.clear()
        notifyDataSetChanged()
    }

    fun addProduct(product: product_representation_cart){
        PersonBuyer.addProductToCart(product)
        views.clear()
        notifyDataSetChanged()
    }

    fun updateProducts() {
        views.clear()
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.product_representation_cart, parent, false)

        who = this

        // Find UI elements in the inflated view
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewTitulo)
        val stockText = view.findViewById<TextView>(R.id.stock)
        ImageURLtoBitmapConverter.downloadImageCart(PersonBuyer.getShoppingCart()[position].image,view)

        textViewPrice.text = PersonBuyer.getShoppingCart()[position].price
        textViewName.text = PersonBuyer.getShoppingCart()[position].name

        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        selectedImageView.tag = R.drawable.ellipse_5

        val selected = view.findViewById<ImageView>(R.id.imageViewSelected)
        selected.setOnClickListener {
            if (selectedImageView.tag == R.drawable.ellipse_5) {
                selectedImageView.setImageResource(R.drawable.cart_selected)
                selectedImageView.tag = R.drawable.cart_selected
                Mediador.notifyItemSelected(PersonBuyer.getShoppingCart()[position])
                Log.i("Selected", PersonBuyer.getShoppingCart()[position].toString())
                Log.i("SelectedPosition", position.toString())
            } else {
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
                Mediador.notifyItemUnselected(PersonBuyer.getShoppingCart()[position])
            }
        }

        var currentStock = PersonBuyer.getShoppingCart()[position].quantity.toInt()
        val stock = PersonBuyer.getShoppingCart()[position].stock

        stockText.text = currentStock.toString()

        val addQuantity = view.findViewById<ImageView>(R.id.addStock)


        addQuantity.setOnClickListener {
            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
                Mediador.notifyItemQuantityIncreased(PersonBuyer.getShoppingCart()[position],view,currentStock)
            }
        }

        val substractQuantity = view.findViewById<ImageView>(R.id.substractStock)
        substractQuantity.setOnClickListener {
            if(currentStock > 1){
                currentStock--
                stockText.text = currentStock.toString()
                Mediador.notifyItemQuantityDecreased(PersonBuyer.getShoppingCart()[position],view,currentStock)
                Log.i("Views",views.toString())
            } else{
                removeProduct(position)
                Mediador.notifyItemDeleted(PersonBuyer.getShoppingCart()[position],view)
                Log.i("Views",views.toString())
            }
        }
        views.add(view)
        return view
    }



    companion object{
        val views = mutableListOf<View>()
        lateinit var who: ProductCartAdapter
        fun setImage(image: Bitmap?,view:View){
            val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
            if (image != null) {
                imageView.setImageBitmap(image)
            }
        }
        fun setAllSelected(){
            for (v in views){
                val selectedImageView = v.findViewById<ImageView>(R.id.imageViewSelected)
                selectedImageView.setImageResource(R.drawable.cart_selected)
                selectedImageView.tag = R.drawable.cart_selected
            }
            Mediador.notifyAllItemsSelected()
            Log.i("Views",views.toString())
        }
        fun setAllUnselected(){
            for (v in views){
                val selectedImageView = v.findViewById<ImageView>(R.id.imageViewSelected)
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
            }
            Mediador.notifyAllItemsUnselected()
            Log.i("Views",views.toString())
        }

    }

}