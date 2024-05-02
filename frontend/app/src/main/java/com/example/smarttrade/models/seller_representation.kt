package com.example.smarttrade.models

import android.icu.number.IntegerWidth

open class seller_representation (
    open var cif : String,
    open val image: String,
    open val ecology: String,
    open val price : String,
    open val name : String,
    open val description: String,
    open val productNumber: String,
    open val category: String,
    open val stock : String,
    open var vendorName: String,
)

data class technology_representation_seller(
    override var cif : String,
    override val image: String,
    override val ecology: String,
    override val price : String,
    override val name : String,
    override val description: String,
    override val productNumber: String,
    override val category: String,
    override val stock : String,
    override var vendorName: String,
    val brand: String,
    val electricConsumption: String
) : seller_representation(cif, image, ecology, price, name, description, productNumber, category, stock, vendorName)

data class clothes_representation_seller(
    override var cif : String,
    override val image: String,
    override val ecology: String,
    override val price : String,
    override val name : String,
    override val description: String,
    override val productNumber: String,
    override val category: String,
    override val stock : String,
    override var vendorName: String,
    val size: String,
    val color : String
) : seller_representation(cif, image, ecology, price, name, description, productNumber, category, stock, vendorName )

data class food_representation_seller(
    override var cif : String,
    override val image: String,
    override val ecology: String,
    override val price : String,
    override val name : String,
    override val description: String,
    override val productNumber: String,
    override val category: String,
    override val stock : String,
    override var vendorName: String,
    val calories: String,
    val macros: String
) : seller_representation(cif, image, ecology, price, name, description, productNumber, category, stock, vendorName)

data class toy_representation_seller(
    override var cif : String,
    override val image: String,
    override val ecology: String,
    override val price : String,
    override val name : String,
    override val description: String,
    override val productNumber: String,
    override val category: String,
    override val stock : String,
    override var vendorName: String,
    val age: String,
    val material: String
) : seller_representation(cif, image, ecology, price, name, description, productNumber, category, stock, vendorName)


