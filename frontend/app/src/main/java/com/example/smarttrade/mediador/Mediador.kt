package com.example.smarttrade.mediador

import android.app.Person
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart

object Mediador {

    var totalPrice = 0.0
    fun notifyItemSelected(product: product_representation_cart){
        Log.i("AddPrice", product.quantity.toString())
        Log.i("AddPrice", product_representation_cart::class.java.toString())
        addPriceToTotal(product.price.toDouble(),product.quantity)
        PersonBuyer.addSelectedItemFromCart(product)
    }
    fun notifyItemUnselected(product: product_representation_cart){
       removePriceFromTotal(product.price.toDouble(),product.quantity)
        PersonBuyer.removeSelectedItemFromCart(product)
    }

    fun notifyItemQuantityIncreased(product: product_representation_cart,view: View, quantity: Int){
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected){
            addPriceToTotal(product.price.toDouble(),1)
            PersonBuyer.modifySelectedItemInCart(product,quantity)
        }
        val p = PersonBuyer.modifyProductInCart(product,quantity)
        ShoppingCartRequests.editProductInCart(p)
    }

    fun notifyItemQuantityDecreased(product: product_representation_cart,view: View, quantity: Int){
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected){
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.modifySelectedItemInCart(product,quantity)
        }
        val p = PersonBuyer.modifyProductInCart(product,quantity)
        ShoppingCartRequests.editProductInCart(p)
    }

    fun notifyItemDeleted(product: product_representation_cart, view: View, pos:Int){
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected) {
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.removeSelectedItemFromCart(product)
        }
        PersonBuyer.removeProductFromCart(pos)
        ShoppingCartRequests.deleteProductInCart(product)
    }

    fun notifyAllItemsSelected(){
        val productList = PersonBuyer.getShoppingCart()
        totalPrice = 0.0
        for (product in productList){
            totalPrice += product.price.toDouble()*product.quantity
        }
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPriceView.text = totalPrice.toString()
        PersonBuyer.setSelectedItemsInCart(PersonBuyer.getShoppingCart())
    }

    fun notifyAllItemsUnselected(){
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPrice = 0.0
        totalPriceView.text = totalPrice.toString()
        PersonBuyer.setSelectedItemsInCart(mutableListOf())
    }

    private fun addPriceToTotal(price: Double, quantity: Int){
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPrice += (price*quantity)
        totalPriceView.text = totalPrice.toString()
    }

    private fun removePriceFromTotal(price: Double, quantity: Int){
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPrice -= (price*quantity)
        totalPriceView.text = totalPrice.toString()
    }

}

