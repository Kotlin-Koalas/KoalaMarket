package com.example.smarttrade.mediador

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.ProductView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart
import kotlin.math.round
import kotlin.properties.Delegates

object MediatorShoppingCart {

    var addProduct = true
    var totalPrice = 0.0
    fun notifyItemSelected(product: product_representation_cart) {
        addPriceToTotal(product.price.toDouble(), product.quantity)
        PersonBuyer.addSelectedItemFromCart(product)
    }

    fun notifyItemUnselected(product: product_representation_cart) {
        removePriceFromTotal(product.price.toDouble(), product.quantity)
        PersonBuyer.removeSelectedItemFromCart(product)
    }

    fun notifyItemQuantityIncreased(
        product: product_representation_cart,
        view: View,
        quantity: Int
    ) {
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if (selectedImageView.tag == R.drawable.cart_selected) {
            addPriceToTotal(product.price.toDouble(), 1)
            PersonBuyer.modifySelectedItemInCart(product, quantity)
        }
        val p = PersonBuyer.modifyProductInCart(product, quantity)
        ShoppingCartRequests.editProductInCart(p)
        Log.i("CartWhenMore", PersonBuyer.getShoppingCart().toString())
    }

    fun notifyItemQuantityDecreased(
        product: product_representation_cart,
        view: View,
        quantity: Int
    ) {
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if (selectedImageView.tag == R.drawable.cart_selected) {
            removePriceFromTotal(product.price.toDouble(), 1)
            PersonBuyer.modifySelectedItemInCart(product, quantity)
        }
        val p = PersonBuyer.modifyProductInCart(product, quantity)
        ShoppingCartRequests.editProductInCart(p)
        Log.i("CartWhenLess", PersonBuyer.getShoppingCart().toString())
    }

    fun notifyItemDeleted(product: product_representation_cart, view: View, pos: Int) {
        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        if (selectedImageView.tag == R.drawable.cart_selected) {
            removePriceFromTotal(product.price.toDouble(), 1)
            PersonBuyer.removeSelectedItemFromCart(product)
        }
        PersonBuyer.removeProductFromCart(pos)
        ShoppingCartRequests.deleteProductInCart(product)
        Log.i("CartWhenDeleted", PersonBuyer.getShoppingCart().toString())
    }

    fun notifyAllItemsSelected() {
        val productList = PersonBuyer.getShoppingCart()
        totalPrice = 0.0
        for (product in productList) {
            totalPrice += product.price.toDouble() * product.quantity
        }
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPriceView.text = totalPrice.toString()
        PersonBuyer.setSelectedItemsInCart(PersonBuyer.getShoppingCart())
    }

    fun notifyItemAdded(
        productExisting: product_representation_cart,
        product: product_representation_cart
    ) {
        Log.i("productExisting", productExisting.toString())
        if (productExisting.seller != "") {
            showCustomDialogBoxWarning("El producto ya se encuentra en el carrito, ¿Desea agregarlo nuevamente?", productExisting, product)
            Log.i("addProduct", addProduct.toString())
        } else {
            PersonBuyer.addProductToCart(product)
            ShoppingCartRequests.addProductToCart(product)
            showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
        }

    }

    fun notifyAllItemsUnselected() {
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        totalPrice = 0.0
        totalPriceView.text = totalPrice.toString()
        PersonBuyer.setSelectedItemsInCart(mutableListOf())
    }

    private fun roundToTwoDecimals(number: Double): Double {
        return round(number * 100.0) / 100.0
    }

    private fun addPriceToTotal(price: Double, quantity: Int) {
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        Log.i("totalPrice", totalPrice.toString())
        Log.i("quantity", quantity.toString())
        Log.i("price", price.toString())
        totalPrice += (price * quantity)
        totalPrice = roundToTwoDecimals(totalPrice)
        totalPriceView.text = totalPrice.toString()
        Log.i("totalPriceAferSelect", totalPrice.toString())
    }

    private fun removePriceFromTotal(price: Double, quantity: Int) {
        val view = ShoppingCartFragment.getCurrView()
        val totalPriceView = view.findViewById<TextView>(R.id.textViewPrecioTotal)
        Log.i("totalPrice", totalPrice.toString())
        Log.i("quantity", quantity.toString())
        Log.i("price", price.toString())
        totalPrice -= (price * quantity)
        totalPrice = roundToTwoDecimals(totalPrice)
        Log.i("totalPriceAferUnselect", totalPrice.toString())
        totalPriceView.text = totalPrice.toString()
    }

    public fun setPriceToCero() {
        totalPrice = 0.0
    }

    fun showCustomDialogBoxWarning(
        msgWarning: String,
        productExisting: product_representation_cart,
        product: product_representation_cart
    ) {
        val dialog = Dialog(ProductView.getContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_warning)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelPopUp)

        btnOk.setOnClickListener {
            val quantity = productExisting.quantity + product.quantity
            Log.i("quantity", quantity.toString())
            val p = product_representation_cart(
                productExisting.cif,
                productExisting.category,
                productExisting.name,
                productExisting.price,
                productExisting.image,
                productExisting.stock,
                productExisting.description,
                productExisting.leafColor,
                productExisting.PN,
                quantity,
                productExisting.seller
            )
            ShoppingCartRequests.editProductInCart(p)
            showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            showCustomDialogBox("Producto no añadido")
            dialog.dismiss()
        }
        messageBox.text = msgWarning
        dialog.show()

    }

    fun showCustomDialogBoxSuccess(msgSuccess: String) {
        val dialog = Dialog(ProductView.getContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        messageBox.text = msgSuccess

        dialog.show()
    }
    fun showCustomDialogBox(popUpText: String) {
        val dialog = Dialog(ProductView.getContext())
        dialog.setTitle("ERROR")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert_login)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener{
            dialog.dismiss()
        }
        messageBox.text = popUpText

        dialog.show()

    }
}



