package com.example.smarttrade.nonactivityclasses

data class product_representation (
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val description: String,
    val leafColor: String,
    val PN: String
)

data class technology_representation (
    val name: String,
    val price: Double,
    val image: String,
    val stock: Int,
    val description: String,
    val leafColor: String,
    val PN: String,
    val brand: String,
    val electricConsumption: Double,
    )

data class clothes_representation(
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val stock: Int,
    val leafColor: String,
    val PN: String,
    val size: String,
    val color : String
)

data class food_representation(
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val stock: Int,
    val leafColor: String,
    val PN: String,
    val calories: String,
    val macros: String

)

data class toy_representation(
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val stock: Int,
    val leafColor: String,
    val PN: String,
    val material :String,
    val recomendAge : String
)




/*
enum class LeafColor{
    RED ="1",
    YELLOW="0",
    GREEN = "-1"
}
* */

