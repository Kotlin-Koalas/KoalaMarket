package com.example.smarttrade.mediador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart

object Mediador {
    //TODO implementar mediador para que al recivir notificacion de un cambio en cantidad de productos, cambie precios totales y cambie el elemento en la lista de carrito del comprador, y en la API.
    fun notifyItemSelected(product: product_representation_cart){
        addPriceToTotal(product.price.toDouble())
        PersonBuyer.addSelectedItemToCart(product)
    }
    fun notifyItemUnselected(product: product_representation_cart){
       removePriceFromTotal(product.price.toDouble())
        PersonBuyer.removeSelectedItemFromCart(product)
    }

    fun notifyItemQuantityIncreased(product: product_representation_cart,view: View){
        ShoppingCartRequests.editProductInCart(product)
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.layout.cart_selected){
            addPriceToTotal(product.price.toDouble())
            PersonBuyer.modifySelectedItemInCart(product.PN,product.quantity)
            //TODO modificar en el carrito del comprador
        }
    }

    fun notifyItemQuantityDecreased(product: product_representation_cart,view: View){
        ShoppingCartRequests.editProductInCart(product)
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.layout.cart_selected){
            removePriceFromTotal(product.price.toDouble())
            PersonBuyer.modifySelectedItemInCart(product.PN,product.quantity)
            //TODO modificar en el carrito del comprador
        }
    }

    private fun addPriceToTotal(price: Double){
        val view = ShoppingCartFragment.getCurrView()
        val totalPrice = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        val oldPrice = totalPrice.text.toString().toDouble()
        val newPrice = oldPrice + price
        totalPrice.text = newPrice.toString()
    }

    private fun removePriceFromTotal(price: Double){
        val view = ShoppingCartFragment.getCurrView()
        val totalPrice = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        val oldPrice = totalPrice.text.toString().toDouble()
        val newPrice = oldPrice - price
        totalPrice.text = newPrice.toString()
    }

}

