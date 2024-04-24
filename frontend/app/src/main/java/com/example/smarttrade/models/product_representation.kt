package com.example.smarttrade.models

open class product_representation (
    open val type: String,
    open val name: String,
    open val price: String,
    open val image: String,
    open val stock: Int,
    open val description: String,
    open val leafColor: String,
    open val PN: String
)

data class technology_representation (
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    val brand: String,
    val electricConsumption: String,
    ) : product_representation("technology", name, price, image, stock, description, leafColor, PN)

data class clothes_representation(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    val size: String,
    val color : String
) : product_representation("clothes", name, price, image, stock, description, leafColor, PN)

data class food_representation(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    val calories: String,
    val macros: String
) : product_representation("food", name, price, image, stock, description, leafColor, PN)

data class toy_representation(
    override val name: String,
    override val price: String,
    override val image: String,
    override val stock: Int,
    override val description: String,
    override val leafColor: String,
    override val PN: String,
    val material :String,
    val age : String
) : product_representation("toy", name, price, image, stock, description, leafColor, PN)




/*
enum class LeafColor{
    RED ="1",
    YELLOW="0",
    GREEN = "-1"
}
* */

