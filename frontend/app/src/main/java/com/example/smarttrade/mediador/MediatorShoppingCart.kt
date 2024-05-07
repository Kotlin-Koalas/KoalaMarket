package com.example.smarttrade.mediador

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart
import kotlin.math.round

object MediatorShoppingCart {

    var totalPrice = 0.0
    fun notifyItemSelected(product: product_representation_cart){
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
        Log.i("CartWhenMore", PersonBuyer.getShoppingCart().toString())
    }

    fun notifyItemQuantityDecreased(product: product_representation_cart,view: View, quantity: Int){
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected){
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.modifySelectedItemInCart(product,quantity)
        }
        val p = PersonBuyer.modifyProductInCart(product,quantity)
        ShoppingCartRequests.editProductInCart(p)
        Log.i("CartWhenLess", PersonBuyer.getShoppingCart().toString())
    }

    fun notifyItemDeleted(product: product_representation_cart, view: View, pos:Int){
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if(selectedImageView.tag == R.drawable.cart_selected) {
            removePriceFromTotal(product.price.toDouble(),1)
            PersonBuyer.removeSelectedItemFromCart(product)
        }
        PersonBuyer.removeProductFromCart(pos)
        ShoppingCartRequests.deleteProductInCart(product)
        Log.i("CartWhenDeleted", PersonBuyer.getShoppingCart().toString())
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

    fun notifyItemAdded(product: product_representation_cart){
        if(ShoppingCartRequests.checkIfExistsProductInCart(product)){
            val productExisting = ShoppingCartRequests.getProductInCart(product.PN)
            val quantity = productExisting.quantity + product.quantity
            val newStock = product_representation_cart(product.cif,product.category,product.name,product.price,product.image,product.stock,product.description,product.leafColor,product.PN,quantity,product.seller)
            ShoppingCartRequests.editProductInCart(newStock)
        }
        else{
            PersonBuyer.addProductToCart(product)
            ShoppingCartRequests.addProductToCart(product)
            Log.i("ProductAdded", product.toString())
        }

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
        Log.i("totalPrice",totalPrice.toString())
        Log.i("quantity",quantity.toString())
        Log.i("price",price.toString())
        totalPrice += (price*quantity)
        totalPrice = roundToTwoDecimalPlaces(totalPrice)
        totalPriceView.text = totalPrice.toString()
        Log.i("totalPriceAferSelect", totalPrice.toString())
    }

    private fun removePriceFromTotal(price: Double, quantity: Int){
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        Log.i("totalPrice",totalPrice.toString())
        Log.i("quantity",quantity.toString())
        Log.i("price",price.toString())
        totalPrice -= (price*quantity)
        totalPrice = roundToTwoDecimalPlaces(totalPrice)
        Log.i("totalPriceAferUnselect", totalPrice.toString())
        totalPriceView.text = totalPrice.toString()
    }

    fun roundToTwoDecimalPlaces(number: Double): Double {
        return round(number * 100) / 100
    }
    public fun setPriceToCero(){
        totalPrice = 0.0
    }
}

