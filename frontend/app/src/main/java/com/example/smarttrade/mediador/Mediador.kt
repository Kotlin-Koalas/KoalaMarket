package com.example.smarttrade.mediador

import android.app.Person
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart

object Mediador {
    fun notifyItemSelected(product: product_representation_cart){
        addPriceToTotal(product.price.toDouble(),product.quantity)
        PersonBuyer.addSelectedItemToCart(product)
    }
    fun notifyItemUnselected(product: product_representation_cart){
       removePriceFromTotal(product.price.toDouble(),product.quantity)
        PersonBuyer.removeSelectedItemFromCart(product)
    }

    fun notifyItemQuantityIncreased(product: product_representation_cart,view: View){
        ShoppingCartRequests.editProductInCart(product)
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected){
            addPriceToTotal(product.price.toDouble(),1)
            PersonBuyer.modifySelectedItemInCart(product.PN,product.quantity)
        }

    }

    fun notifyItemQuantityDecreased(product: product_representation_cart,view: View){
        ShoppingCartRequests.editProductInCart(product)
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected){
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.modifySelectedItemInCart(product.PN,product.quantity)
        }
        PersonBuyer.modifyProductInCart(product.PN,product.quantity)
    }

    fun notifyItemDeleted(product: product_representation_cart, view: View){
        ShoppingCartRequests.deleteProductInCart(product)
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected) {
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.removeSelectedItemFromCart(product)
        }
    }

    fun notifyAllItemsSelected(){
        val productList = PersonBuyer.getShoppingCart()
        var totalPrice: Double = 0.0
        for (product in productList){
            totalPrice += product.price.toDouble()
        }
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPriceView.text = totalPrice.toString()
        PersonBuyer.setSelectedItemsInCart(PersonBuyer.getShoppingCart())
    }

    fun notifyAllItemsUnselected(){
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        val none = 0.0
        totalPriceView.text = none.toString()
        PersonBuyer.setSelectedItemsInCart(mutableListOf())
    }

    private fun addPriceToTotal(price: Double, quantity: Int){
        val view = ShoppingCartFragment.getCurrView()
        val totalPrice = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        val oldPrice = totalPrice.text.toString().toDouble()
        val newPrice = oldPrice + (price*quantity)
        totalPrice.text = newPrice.toString()
    }

    private fun removePriceFromTotal(price: Double, quantity: Int){
        val view = ShoppingCartFragment.getCurrView()
        val totalPrice = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        val oldPrice = totalPrice.text.toString().toDouble()
        val newPrice = oldPrice - (price*quantity)
        totalPrice.text = newPrice.toString()
    }

}

