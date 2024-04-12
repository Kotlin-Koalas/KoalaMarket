package com.example.smarttrade.nonactivityclasses

data class product_representation (
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long
)

data class technology_representation (
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long,
    val brand: String,
    val electricConsumption: String,

    )

data class clothes_representation(
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long,
    val size: String,
    val color : String
)

data class food_representation(
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long,
    val calories: String,
    val macros: String

)

data class toy_representation(
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long,
    val material :String,
    val recomendAge : String
)








enum class LeafColor{
    RED, YELLOW, GREEN
}
