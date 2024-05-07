package com.example.smarttrade.models

import java.io.Serializable

open class product_representation_cart (
    open val cif: String,
    open val category: String,
    open val name: String,
    open val price: String,
    open val image: String,
    open val stock: Int,
    open val description: String,
    open val leafColor: String,
    open val PN: String,
    open var quantity: Int,
    open var seller: String
):Serializable

data class technology_representation_cart (
    override val cif: String,
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    override var seller: String,
    val brand: String,
    val electricConsumption: String,
    ) : product_representation_cart(cif,"technology", name, price, image, stock, description, leafColor, PN, quantity, seller)

data class clothes_representation_cart(
    override val cif: String,
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    override var seller: String,
    val size: String,
    val color : String
) : product_representation_cart(cif,"clothes", name, price, image, stock, description, leafColor, PN,quantity, seller)

data class food_representation_cart(
    override val cif: String,
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    override var seller: String,
    val calories: String,
    val macros: String
) : product_representation_cart(cif,"food", name, price, image, stock, description, leafColor, PN,quantity, seller)

data class toy_representation_cart(
    override val cif: String,
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    override var seller: String,
    val material :String,
    val age : String
) : product_representation_cart(cif,"toy", name, price, image, stock, description, leafColor, PN,quantity, seller)




/*
enum class LeafColor{
    RED ="1",
    YELLOW="0",
    GREEN = "-1"
}
* */

