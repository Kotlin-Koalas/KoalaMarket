package com.example.smarttrade.models

import android.util.Log
import com.example.smarttrade.adapters.ProductCartAdapter

object  PersonBuyer {
    private var name: String = ""
    private var surnames: String = ""
    private var email: String = ""
    private var userID: String = ""
    private var password: String = ""
    private var shippingAddresses: MutableList<String> = mutableListOf()
    private var DNI: String = ""
    private var factAddresses: MutableList<String> = mutableListOf()
    private var bizum: String = ""
    private var paypal: String = ""
    private var creditCards: MutableList<CreditCard> = mutableListOf()
    private var shoppingCart: MutableList<product_representation_cart> = mutableListOf()
    private var selectedItemsCart: MutableList<product_representation_cart> = mutableListOf()
    private var wishList: MutableList<product_representation_cart> = mutableListOf()
    private var selectedItemsWish: MutableList<product_representation_cart> = mutableListOf()


    fun setName(name: String) {
        this.name = name
    }

    fun setSurname(surname: String) {
        surnames = surname
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setUserId(uID: String) {
        userID = uID
    }

    fun setPassword(pass: String) {
        password = pass
    }

    fun getName(): String {
        return name
    }

    fun getSurname(): String {
        return surnames
    }

    fun getEmail(): String {
        return email
    }

    fun getUserId(): String {
        return userID
    }

    fun getPassword(): String {
        return password
    }

    fun addShippingAddress(address: String) {
        shippingAddresses.add(address)
    }

    fun clearShippingAddresses() {
        shippingAddresses.clear()
    }

    fun getShippingAddresses(): MutableList<String> {
        return shippingAddresses
    }

    fun setDNI(DNI: String) {
        this.DNI = DNI
    }

    fun getDNI(): String {
        return DNI
    }

    fun addFacturacionAddress(address: String) {
        factAddresses.add(address)
    }

    fun clearFacturationAddresses() {
        factAddresses.clear()
    }

    fun getFacturationAddresses(): MutableList<String> {
        return factAddresses
    }

    fun setBizum(Bizum: String) {
        this.bizum = Bizum
    }

    fun getBizum(): String {
        return bizum
    }

    fun setPaypal(Paypal: String) {
        this.paypal = Paypal
    }

    fun getPaypal(): String {
        return paypal
    }

    fun addCreditCard(creditCard: CreditCard) {
        creditCards.add(creditCard)
    }

    fun getCreditCards(): MutableList<CreditCard> {
        return creditCards
    }

    fun addProductToCart(product: product_representation_cart) {
        Log.i("Entra dentro del método", "Toma")
        shoppingCart.add(product)
        Log.i("Producto añadido", product.toString())
    }

    fun getShoppingCart(): MutableList<product_representation_cart> {
        return shoppingCart
    }

    fun removeProductFromCart(product: product_representation_cart) {
        shoppingCart.remove(product)
    }

    fun clearShoppingCart() {
        shoppingCart.clear()
    }

    fun removeProductFromCart(position: Int) {
        Log.i("Delete Product Position", position.toString())
        Log.i("Delete Product", shoppingCart.toString())
        shoppingCart.removeAt(position)
        ProductCartAdapter.updateProducts()
    }

    fun setProductsInCart(products: MutableList<product_representation_cart>) {
        shoppingCart = products
    }

    fun addSelectedItemFromCart(product: product_representation_cart) {
        selectedItemsCart.add(product)
    }

    fun setSelectedItemsInCart(products: MutableList<product_representation_cart>) {
        selectedItemsCart.clear()
        for (p in products) {
            selectedItemsCart.add(p)
        }
    }

    fun getSelectedItemsCart(): MutableList<product_representation_cart> {
        return selectedItemsCart
    }

    fun removeSelectedItemFromCart(product: product_representation_cart) {
        selectedItemsCart.remove(product)
    }

    fun getTotalPrice(): Double {
        var total = 0.0
        for (p in selectedItemsCart) {
            total += p.price.toDouble() * p.quantity
        }
        return total
    }

    fun modifySelectedItemInCart(
        product: product_representation_cart,
        quantity: Int
    ): product_representation_cart {
        for (p in selectedItemsCart) {
            if (p == product) {
                p.quantity = quantity
                return p
            }
        }
        return product_representation_cart("","", "", "", "", 0, "", "", "", 0, "")
    }

    fun clearSelectedItems(){
        selectedItemsCart.clear()
    }

    fun modifyProductInCart(
        product: product_representation_cart,
        quantity: Int
    ): product_representation_cart {
        for (p in shoppingCart) {
            if (p == product) {
                p.quantity = quantity
                return p
            }
        }
        return product_representation_cart("","", "", "", "", 0, "", "", "", 0, "")
    }

    fun getWishList(): MutableList<product_representation_cart> {
        return wishList
    }

    fun removeProductFromWish(position: Int) {
        Log.i("Delete Product Position", position.toString())
        Log.i("Delete Product", wishList.toString())
        wishList.removeAt(position)
        //ProductWishAdapter.updateProducts()
    }

    fun removeProductFromWish(product: product_representation_cart) {
        wishList.remove(product)
    }

    fun removeSelectedItemFromWish(product: product_representation_cart) {
        selectedItemsWish.remove(product)
    }

    fun addProductToWish(product: product_representation_cart) {
        Log.i("Entra dentro del método", "Toma")
        wishList.add(product)
        Log.i("Producto añadido", product.toString())
    }

    fun clearWishList() {
        wishList.clear()
    }


}