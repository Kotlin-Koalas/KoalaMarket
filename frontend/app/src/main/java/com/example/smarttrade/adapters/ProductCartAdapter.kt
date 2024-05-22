package com.example.smarttrade.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.R
import com.example.smarttrade.mediador.MediatorShoppingCart
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

        var currentStock = PersonBuyer.getShoppingCart()[position].quantity

        val selected = view.findViewById<ImageView>(R.id.imageViewSelected)
        selected.setOnClickListener {
            if (selectedImageView.tag == R.drawable.ellipse_5) {
                selectedImageView.setImageResource(R.drawable.cart_selected)
                selectedImageView.tag = R.drawable.cart_selected
                MediatorShoppingCart.notifyItemSelected(PersonBuyer.getShoppingCart()[position])
            } else {
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
                MediatorShoppingCart.notifyItemUnselected(PersonBuyer.getShoppingCart()[position])
            }
        }

        val stock = PersonBuyer.getShoppingCart()[position].stock

        stockText.text = currentStock.toString()

        val addQuantity = view.findViewById<ConstraintLayout>(R.id.addStock)


        addQuantity.setOnClickListener {
            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
                MediatorShoppingCart.notifyItemQuantityIncreased(PersonBuyer.getShoppingCart()[position],view,currentStock)
            }
        }

        val substractQuantity = view.findViewById<ConstraintLayout>(R.id.substractStock)
        substractQuantity.setOnClickListener {
            if(currentStock > 1){
                currentStock--
                stockText.text = currentStock.toString()
                MediatorShoppingCart.notifyItemQuantityDecreased(PersonBuyer.getShoppingCart()[position],view,currentStock)
                Log.i("Views",views.toString())
            } else{
                MediatorShoppingCart.notifyItemDeleted(PersonBuyer.getShoppingCart()[position],view,position)
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
            MediatorShoppingCart.notifyAllItemsSelected()
        }
        fun setAllUnselected(){
            for (v in views){
                val selectedImageView = v.findViewById<ImageView>(R.id.imageViewSelected)
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
            }
            MediatorShoppingCart.notifyAllItemsUnselected()
            Log.i("Views",views.toString())
        }

        fun updateProducts(){
            who.updateProducts()
        }



    }

}