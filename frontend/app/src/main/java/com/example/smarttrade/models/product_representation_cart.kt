package com.example.smarttrade.models

open class product_representation_cart (
    open val type: String,
    open val name: String,
    open val price: String,
    open val image: String,
    open val stock: Int,
    open val description: String,
    open val leafColor: String,
    open val PN: String,
    open var quantity: Int
)

data class technology_representation_cart (
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    val brand: String,
    val electricConsumption: String,
    ) : product_representation_cart("technology", name, price, image, stock, description, leafColor, PN, quantity)

data class clothes_representation_cart(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    val size: String,
    val color : String
) : product_representation_cart("clothes", name, price, image, stock, description, leafColor, PN,quantity)

data class food_representation_cart(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    val calories: String,
    val macros: String
) : product_representation_cart("food", name, price, image, stock, description, leafColor, PN,quantity)

data class toy_representation_cart(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    override var quantity: Int,
    val material :String,
    val age : String
) : product_representation_cart("toy", name, price, image, stock, description, leafColor, PN,quantity)




/*
enum class LeafColor{
    RED ="1",
    YELLOW="0",
    GREEN = "-1"
}
* */

